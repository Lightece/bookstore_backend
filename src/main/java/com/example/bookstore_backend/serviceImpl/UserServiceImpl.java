package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.Dao.OrderDao;
import com.example.bookstore_backend.Dao.UserDao;
import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.entity.UserAuth;
import com.example.bookstore_backend.model.UserBuy;
import com.example.bookstore_backend.service.UserService;
import com.example.bookstore_backend.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private TimerService timerService;

    public Msg AutoLogin(HttpSession session, Cookie[] cookies){
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("sessionId")){
                    // 有cookie,检查session是否存在
                    if(session.getAttribute("user") != null){
                        // session存在,直接返回
                        System.out.println("Login successfully!");
                        return new Msg("Login successfully!", true, session.getAttribute("user"));
                    }
                }
            }
        }
        return new Msg("User does not login!", false, null);
    }

    // 通过验证账号密码登录
    public Msg Login(HttpSession session, Cookie[] cookies,Integer userid, String password ){
        UserAuth userAuth = userDao.findByUserid(userid);
        if(userAuth == null){
//            System.out.println("User does not exist!");
            return new Msg("User does not exist!", false, null);
        }
        else if(!userAuth.getPassword().equals(password)){
//            System.out.println("Wrong password!");
            return new Msg("Wrong password!", false, null);
        }
        else if(userAuth.getType()==2){
//            System.out.println("User is banned!");
            return new Msg("User is banned!", false, null);
        }
        else{
            System.out.println("Login successfully!");
            // 登录成功,新建session, 生成cookie
            User user = userDao.getUserByUserid(userid);
            session.setAttribute("user", user);
            Cookie cookie = new Cookie("sessionId", session.getId());
            // 设置Cookie的有效期
            cookie.setMaxAge(3600); // 1 hour
            List<Object> result = new ArrayList<>();
            result.add(user);
            result.add(cookie);
            timerService.StartTimer(session);
            return new Msg("Login successfully!", true, result);
        }
    }

    public Msg Logout(HttpSession session){
        if(session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                Integer duration = timerService.StopTimer(session);
                session.removeAttribute("user");
                session.invalidate();
                return new Msg("Logout successfully!", true, duration);
            }
        }
        return new Msg("User does not login!", false, null);
    }


    public Msg CheckUserStatus(Integer userid, String token){
        UserAuth userAuth = userDao.findByUserid(userid);
        if(userAuth == null){
            return new Msg("User does not exist!", false, null);
        }
        else if(userAuth.getType()<0){
            return new Msg("User is banned!", false, null);
        }
        else{
            return new Msg("Check successfully!", true, null);
        }
    }

    public Msg getUserInfo(Integer userid, String token){
        UserAuth userAuth = userDao.findByUserid(userid);
        if(userAuth == null){
            return new Msg("User does not exist!", false, null);
        }
//        else if(!userAuth.getToken().equals(token)){
//            return new Msg("Wrong token!", false, null);
//        }
        else{
            User user = userDao.getUserByUserid(userid);
            return new Msg("Get user info successfully!", true, user);
        }
    }

    public Msg EditUserInfo(Integer userid, String token, String nickname, String tel, String address, String email, String avatar){
        UserAuth userAuth = userDao.findByUserid(userid);
        if(userAuth == null){
            return new Msg("User does not exist!", false, null);
        }
//        else if(!userAuth.getToken().equals(token)){
//            return new Msg("Wrong token!", false, null);
//        }
        else{
            User user = userDao.getUserByUserid(userid);
            user.setTel(tel);
            user.setNickname(nickname);
            user.setEmail(email);
            user.setAvatar(avatar);
            user.setAddress(address);
            userDao.saveUser(user);
            return new Msg("Edit user info successfully!", true, user);
        }
    }

    public Msg SetUserStatus(Integer adminId, String adminToken, Integer userid, Integer type){
        UserAuth adminAuth = null;
//        userDao.findByUseridAndToken(adminId, adminToken);
        if(adminAuth == null){
            return new Msg("No administration authorization!", false, null); //没有权限更改
        }
        else{
            UserAuth userAuth = userDao.findByUserid(userid);
            if(userAuth == null){
                return new Msg("User does not exist!", false, null);
            }
            userAuth.setType(type);
            userDao.saveUserAuth(userAuth);
            return new Msg("Set user status successfully!", true,null);
        }
    }
    public Msg checkAdmin(Integer adminId, String adminToken){
        UserAuth adminAuth = null;
//        userDao.findByUseridAndToken(adminId, adminToken);
        if(adminAuth == null || adminAuth.getType()!=1){
            return new Msg("No administration authorization!", false, null); //没有权限更改
        }
        else{
            return new Msg("Check admin successfully!", true,null);
        }
    }

    @Override
    public boolean checkDuplicate(int userid){
        UserAuth userAuth = userDao.findByUserid(userid);
        if(userAuth == null){
            return false;
        }
        else return true;
    }

    @Override
    public Msg register(Integer userid, String password, String nickname, String email, String tel, String avatar, String address){
        UserAuth userAuth = userDao.findByUserid(userid);
        if(userAuth != null){
            return new Msg("User already exists!", false, null);
        }
        else{
            User user = new User(userid, nickname, email, tel, avatar, address);
            userDao.saveUser(user);
            UserAuth auth = new UserAuth(userid, password, 0);
            userDao.saveUserAuth(auth);
            return new Msg("Register successfully!", true, null);
        }
    }

    @Override
    public Msg getUserList(int type){
        return new Msg("Get user list successfully!", true, userDao.findAllByType(type));
    }

    @Override
    public Msg getUsersBuy(String startDate, String endDate){
        List<User> users = userDao.findAllByType(0);    // 0: normal user
        users.addAll(userDao.findAllByType(2)); // 2: banned user
        List<UserBuy> userBuyList = new ArrayList<UserBuy>();
        for(User user: users){
            List<Order> orders = orderDao.findFilteredOrders(user,"", startDate, endDate);
            Double total = 0.0;
            for(Order order: orders){
                total += order.getTotal();
            }
            userBuyList.add(new UserBuy(user, total));
        }
        userBuyList.sort((o1, o2) -> o2.getTotal().compareTo(o1.getTotal()));
        return new Msg("Get users buy successfully!", true, userBuyList);
    }
}
