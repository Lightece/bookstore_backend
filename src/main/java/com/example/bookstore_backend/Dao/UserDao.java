package com.example.bookstore_backend.Dao;

import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.entity.UserAuth;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public UserAuth findByUserid(@Param("userid")Integer userid);
    public UserAuth findByUseridAndToken(@Param("userid") Integer userid, @Param("token")String token);
    public User getUserByUserid(@Param("userid") Integer userid);
    public void saveUser(@Param("user") User user);
    public void saveUserAuth(@Param("userAuth") UserAuth userAuth);
}
