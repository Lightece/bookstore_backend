package com.example.bookstore_backend.controller;
/*
UserController:
    * login  Tested
    * checkUser  TESTED
    * getUserInfo  TESTED
    * add logout
 */
import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.entity.UserAuth;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin( "http://localhost:3000")
@Scope(value="singleton")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    // 用户输入账号密码，登录
    @PostMapping("login")
    public Msg login(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
        // 获取请求参数
        Integer userid = Integer.parseInt((String) params.get("userid"));
        String password = (String) params.get("password");
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        // 账号密码登录
        Msg wrapped = userService.Login(session, cookies, userid, password);
        List<Object> data = (List<Object>) wrapped.getData();
        if(wrapped.isOk()){
            User user = (User) data.get(0);
            Cookie cookie = (Cookie) data.get(1);
            response.addCookie(cookie);
            return new Msg("Login Success!", true, user);
        }
        else return wrapped;
    }

//    // TODO: AutoLogin,点击登录键根据已保存的cookies登录
//    @GetMapping("autoLogin")
//    public Msg autoLogin(HttpServletRequest request, HttpServletResponse response){
//        // 检查session
//        HttpSession session = request.getSession();
//        if(session.getAttribute("userid") != null){
//            int userid = (int) session.getAttribute("userid");
////            String token = (String) session.getAttribute("token");
//            return userService.CheckUserStatus(userid, token);
//        }
//        // 检查cookies
//        Cookie[] cookies = request.getCookies();
//        if(cookies != null){
//            for(Cookie cookie: cookies){
//                if(cookie.getName().equals("userid")){
//                    int userid = Integer.parseInt(cookie.getValue());
//                    String token = cookie.getValue();
//                    return userService.CheckUserStatus(userid, token);
//                }
//            }
//        }
//    }

    @PostMapping("logout")
    public Msg logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        return userService.Logout(session);
    }


    @PostMapping("checkUser")
    public Msg checkUser(@RequestBody Map<String, Object> params){
        int userid = Integer.parseInt((String) params.get("userid"));
        String token = (String) params.get("token");
        Msg msg = userService.CheckUserStatus(userid, token);
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
        String address = (String) params.get("address");
        String avatar = (String) params.get("avatar");
        return userService.register(userid, password, nickname, email, tel, avatar, address);
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