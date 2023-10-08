package com.example.bookstore_backend.service;

import com.example.bookstore_backend.model.Msg;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;


public interface UserService {
    Msg Login(HttpSession session, Cookie[] cookies, Integer userid, String password );
    Msg Logout(HttpSession session);

    Msg CheckUserStatus(Integer userid, String token);
    Msg getUserInfo(Integer userid, String token);
    Msg EditUserInfo(Integer userid, String token, String nickname, String tel, String address, String email, String avatar);
    Msg SetUserStatus(Integer adminId, String adminToken, Integer userid, Integer type);
    Msg checkAdmin(Integer adminId, String adminToken);
    boolean checkDuplicate(int userid);
    Msg register(Integer userid,String password, String nickname, String email, String tel, String avatar, String address);

    Msg getUserList(int type);

    Msg getUsersBuy(String startDate,String endDate);
}
