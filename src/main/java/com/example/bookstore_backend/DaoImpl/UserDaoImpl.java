package com.example.bookstore_backend.DaoImpl;

import com.example.bookstore_backend.Dao.UserDao;
import com.example.bookstore_backend.Repository.UserAuthRepository;
import com.example.bookstore_backend.Repository.UserRepository;
import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.entity.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.OnClose;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public UserAuth findByUserid(@Param("userid")Integer userid){
        return userAuthRepository.findByUserid(userid);
    }
    @Override
    public UserAuth findByUseridAndToken(@Param("userid") Integer userid, @Param("token")String token){
        return userAuthRepository.findByUseridAndToken(userid, token);
    }

    @Override
    public User getUserByUserid(@Param("userid") Integer userid){
        return userRepository.findByUserid(userid);
    }

    @Override
    public void saveUser(@Param("user") User user){
        userRepository.save(user);
    }
    @Override
    public void saveUserAuth(@Param("userAuth") UserAuth userAuth){
        userAuthRepository.save(userAuth);
    }
}
