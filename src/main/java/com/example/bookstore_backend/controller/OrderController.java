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

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/getOrders")
    public Msg getOrders(@RequestBody Map<String, Object> params){
        Integer userid = Integer.valueOf((String)params.get("userid"));
        String token = (String)params.get("token");
        if(!userService.CheckUserStatus(userid, token).isOk()) return new Msg("User not logged in!", false, null);
        return orderService.getOrderList(userid, "", "", "");
    }

//    @PostMapping("/getOrderList")
//    public Msg getOrderList(@RequestBody Map<String, Object> params){
//        System.out.println(params);
//        Integer userid = Integer.valueOf((String)params.get("userid"));
//        String token = (String)params.get("token");
//        boolean isAdmin = (boolean)params.get("isAdmin"); // admin request, auth and get order of all users
//        String keyword = (String)params.get("keyword");
//        String startDate = (String)params.get("startDate");
//        String endDate = (String)params.get("endDate");
//        if(isAdmin){
//            if(!userService.checkAdmin(userid, token).isOk()) return new Msg("User is not admin!", false, null);
//            return orderService.getAllOrders(keyword, startDate, endDate);
//        }
//        if(!userService.CheckUserStatus(userid, token).isOk()) return new Msg("User not logged in!", false, null);
//        return orderService.getOrderList(userid, keyword, startDate, endDate);
//    }
}
