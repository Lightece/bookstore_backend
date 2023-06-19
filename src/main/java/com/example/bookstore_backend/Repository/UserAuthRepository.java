package com.example.bookstore_backend.Repository;

import com.example.bookstore_backend.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {
    public UserAuth findByUserid(@Param("userid")Integer userid);
    public UserAuth findByUseridAndToken(@Param("userid") Integer userid, @Param("token")String token);
}
