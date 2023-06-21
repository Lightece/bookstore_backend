package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.Dao.OrderDao;
import com.example.bookstore_backend.Dao.UserDao;
import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.entity.UserAuth;
import com.example.bookstore_backend.model.UserBuy;
import com.example.bookstore_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;


    public Msg Login(Integer userid, String password){
        // print current time
        System.out.print( new java.util.Date() );
        System.out.print("\tLogin: userid=");System.out.print(userid);System.out.print(" password=");System.out.println(password);

        UserAuth userAuth = userDao.findByUserid(userid);
        if(userAuth == null){
            System.out.println("User does not exist!");
            return new Msg("User does not exist!", false, null);
        }
        else if(!userAuth.getPassword().equals(password)){
            System.out.println("Wrong password!");
            return new Msg("Wrong password!", false, null);
        }
        else if(userAuth.getType()==2){ // banned
            return new Msg("User is banned!", false, null);
        }
        else{
            User user = userDao.getUserByUserid(userid);
            // generate new token
            String token = "";
            for(int i = 0; i < 10; i++){
                token += (char) (Math.random() * 26 + 'a');
            }
            userAuth.setToken(token);
            userDao.saveUserAuth(userAuth);
            user.setToken(token);
            userDao.saveUser(user);

            System.out.print("Login successfully! token=");System.out.println(token);
            return new Msg("Login successfully!", true, user);
        }
    }
    public Msg CheckUserStatus(Integer userid, String token){
        UserAuth userAuth = userDao.findByUserid(userid);
        if(userAuth == null){
            return new Msg("User does not exist!", false, null);
        }
        else if(!userAuth.getToken().equals(token)){
            return new Msg("Wrong token!", false, null);
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
        else if(!userAuth.getToken().equals(token)){
            return new Msg("Wrong token!", false, null);
        }
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
        else if(!userAuth.getToken().equals(token)){
            return new Msg("Wrong token!", false, null);
        }
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
        UserAuth adminAuth = userDao.findByUseridAndToken(adminId, adminToken);
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
        UserAuth adminAuth = userDao.findByUseridAndToken(adminId, adminToken);
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

    //TODO
    @Override
    public Msg register(Integer userid, String password, String nickname, String email, String tel){
    return new Msg("Register successfully!", true, null);
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
