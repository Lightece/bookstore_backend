package com.example.bookstore_backend.controller;
/*
UserController:
    * login  Tested
    * checkUser  TESTED
    * getUserInfo  TESTED
 */
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin( "http://localhost:3000")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("login")
    @ResponseBody
    public Msg login(@RequestBody Map<String, Object> params){
        int userid = Integer.parseInt((String) params.get("userid"));
        String password = (String) params.get("password");
        return userService.Login(userid, password);
    }

    @PostMapping("checkUser")
    public Msg checkUser(@RequestBody Map<String, Object> params){
        System.out.println("checkUser:");
        int userid = Integer.parseInt((String) params.get("userid"));
        String token = (String) params.get("token");
        Msg msg = userService.CheckUserStatus(userid, token);
        System.out.println(msg.isOk());
        return msg;
    }

    @PostMapping("getUserInfo")
    public Msg getUserInfo(@RequestBody Map<String, Object> params){
        System.out.print("getUserInfo:");
        int userid = Integer.parseInt((String) params.get("userid"));
        String token = (String) params.get("token");
        Msg msg = userService.getUserInfo(userid, token);
        System.out.println(msg);
        return msg;
    }
}