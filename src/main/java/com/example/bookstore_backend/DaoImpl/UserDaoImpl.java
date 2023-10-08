package com.example.bookstore_backend.DaoImpl;

import com.example.bookstore_backend.Dao.UserDao;
import com.example.bookstore_backend.Repository.UserAuthRepository;
import com.example.bookstore_backend.Repository.UserRepository;
import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.entity.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public UserAuth findByUserid(@Param("userid")Integer userid){
        return userAuthRepository.findByUserauthid(userid);
    }
//    @Override
//    public UserAuth findByUseridAndToken(@Param("userid") Integer userid, @Param("token")String token){
//        return userAuthRepository.findByUserauthidAndToken(userid, token);
//    }

    @Override
    public User getUserByUserid(@Param("userid") Integer userid){
        return userRepository.findByUserid(userid);
    }

    @Override
    public void saveUser(@Param("user") User user){
        userRepository.save(user);
    }
    @Override
    public UserAuth saveUserAuth(@Param("userAuth") UserAuth userAuth){
        return userAuthRepository.save(userAuth);
    }
    @Override
    public List<User> findAllByType(@Param("type") Integer type){
        List<UserAuth> userAuthList = userAuthRepository.findAllByType(type);
        List<User> userList = new ArrayList<>();
        for(UserAuth userAuth: userAuthList){
            userList.add(userRepository.findByUserid(userAuth.getUserauthid()));
        }
        return userList;
    }
}
