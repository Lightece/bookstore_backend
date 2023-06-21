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
//        System.out.println("checkUser:");
        int userid = Integer.parseInt((String) params.get("userid"));
        String token = (String) params.get("token");
        Msg msg = userService.CheckUserStatus(userid, token);
//        System.out.println(msg.isOk());
        return msg;
    }

    @PostMapping("checkAdmin")
    public Msg checkAdmin(@RequestBody Map<String, Object> params){
        int userid = Integer.parseInt((String) params.get("userid"));
        String token = (String) params.get("token");
        if(userService.checkAdmin(userid, token).isOk())
            return new Msg("Welcome, Administrator!", true, null);
        return new Msg("User is not Admin!", false, null);
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

    @PostMapping("register")
    public Msg register(@RequestBody Map<String, Object> params){
        int userid = Integer.parseInt((String) params.get("userid"));
        String password = (String) params.get("password");
        String nickname = (String) params.get("nickname");
        String email = (String) params.get("email");
        String tel = (String) params.get("tel");
        return userService.register(userid, password, nickname, email, tel);
    }

    @PostMapping("setUserStatus")
    public Msg setUserStatus(@RequestBody Map<String, Object> params){
        System.out.println(params);
        int userid = Integer.parseInt((String) params.get("adminid"));
        int modify_userid = (Integer)params.get("userid");
        String token = (String) params.get("token");
        if(!userService.checkAdmin(userid, token).isOk()){
            return new Msg("User is not Admin!",false, null);
        }
        int status = (Integer)params.get("status");
        return userService.SetUserStatus(userid, token, modify_userid, status);
    }

    @GetMapping("checkUserid")
    public Msg checkUserid(@RequestParam("userid") Integer userid){
        if(userService.checkDuplicate(userid)){
            return new Msg("Userid is available!", true, null);
        }
        else return new Msg("Userid has been occupied!", false, null);
    }

    @PostMapping("getUserList")
    public Msg getUserList(@RequestBody Map<String, Object> params){
        int userid = Integer.parseInt((String) params.get("userid"));
        String token = (String) params.get("token");
        int type = (Integer)params.get("type");
        if(!userService.checkAdmin(userid, token).isOk()){
            return new Msg("User is not Admin!",false, null);
        }
        return userService.getUserList(type);
    }

    @PostMapping("getUserBuy")
    public Msg getUserBuy(@RequestBody Map<String, Object> params){
        int userid = Integer.parseInt((String) params.get("userid"));
        String token = (String) params.get("token");
        if(!userService.checkAdmin(userid, token).isOk()){
            return new Msg("User is not Admin!",false, null);
        }
        String startDate = (String)params.get("startDate");
        String endDate = (String)params.get("endDate");
        return userService.getUsersBuy(startDate, endDate);
    }
}