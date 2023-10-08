package com.example.bookstore_backend.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
@Scope(value="session")
public interface TimerService {
    void StartTimer(HttpSession session);
    Integer StopTimer(HttpSession session);
}
