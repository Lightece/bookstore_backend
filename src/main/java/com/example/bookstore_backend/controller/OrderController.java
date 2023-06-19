package com.example.bookstore_backend.controller;
/*
OrderController: used to storage and manage o
functions:
- getOrders: get all orders of a user   TESTED
 */

import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.serviceImpl.OrderServiceImpl;
import com.example.bookstore_backend.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private UserServiceImpl userService;

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/getOrders")
    public Msg getOrders(@RequestBody Map<String, Object> params){
        Integer userid = Integer.valueOf((String)params.get("userid"));
        String token = (String)params.get("token");
        if(!userService.CheckUserStatus(userid, token).isOk()) return new Msg("User not logged in!", false, null);
        return orderService.getOrderList(userid);
    }
}
