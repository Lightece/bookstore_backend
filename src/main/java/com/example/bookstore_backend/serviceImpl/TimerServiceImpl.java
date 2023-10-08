package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.service.TimerService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TimerServiceImpl implements TimerService {

    private Map<String, LocalDateTime> userLoginTimeMap = new HashMap<>();

    public void StartTimer(HttpSession session){
        userLoginTimeMap.put(session.getId(), LocalDateTime.now());
    }

    public Integer StopTimer(HttpSession session){
        LocalDateTime loginTime = userLoginTimeMap.get(session.getId());
        if(loginTime == null){
            return null;
        }
        else {
            userLoginTimeMap.remove(session.getId());
            long seconds = Duration.between(loginTime, LocalDateTime.now()).getSeconds();
            return (int)seconds;
        }
    }
}
