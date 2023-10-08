package com.example.bookstore_backend.utils;

import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.model.Cart2Order;
import com.example.bookstore_backend.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

@Component
public class Listener {
    @Autowired
    private OrderService orderService;

    @Autowired
    private WebSocketServer ws;

    @KafkaListener(topics = "buy", groupId = "group_id")
    public void buyListener(String message){
//        System.out.println("Received Order: " + message);
        Cart2Order cart2Order = null;
        try {
            cart2Order = new ObjectMapper().readValue(message, Cart2Order.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderService.submitOrder(cart2Order);
    }

    @KafkaListener(topics = "order", groupId = "group_id")
    public void orderListener(String message){
        System.out.println("Received Order: " + message);
        // websocket向前端返回成功购买的订单信息
        ObjectMapper mapper = new ObjectMapper();
        Order order = null;
        try{
            order = mapper.readValue(message, Order.class);
        } catch (IOException e){
            e.printStackTrace();
        }
        ws.sendMessageToUser(order.getUser().getUserid(), message);
    }

}
