package com.example.bookstore_backend.utils;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/webSocket/{userId}")
@Component
public class WebSocketServer {

    public WebSocketServer() {
        //每当有一个连接，都会执行一次构造方法
    }

    private static final ConcurrentHashMap<Integer, Session> SESSIONS = new ConcurrentHashMap<>();

    public void sendMessage(Session toSession, String message) {
        if (toSession != null) {
            try {
                toSession.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("对方不在线");
        }
    }

    public void sendMessageToUser(Integer userId, String message) {
        System.out.println(userId);
        Session toSession = SESSIONS.get(userId);
        sendMessage(toSession, message);
        System.out.println(message);
    }


    @OnMessage
    public void onMessage(String message) {
        System.out.println("服务器收到消息：" + message);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {
        if (SESSIONS.get(userId) != null) {
            System.out.println("已经上线过了");
            return;
        }
        SESSIONS.put(userId, session);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        SESSIONS.remove(userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }
}



