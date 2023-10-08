package com.example.bookstore_backend.Dao;

import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.entity.UserAuth;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    UserAuth findByUserid(@Param("userid")Integer userid);
//    UserAuth findByUseridAndToken(@Param("userid") Integer userid, @Param("token")String token);
    User getUserByUserid(@Param("userid") Integer userid);
    void saveUser(@Param("user") User user);
    UserAuth saveUserAuth(@Param("userAuth") UserAuth userAuth);

    List<User> findAllByType(@Param("type") Integer type);
}
