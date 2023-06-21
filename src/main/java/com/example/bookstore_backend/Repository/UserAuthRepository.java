package com.example.bookstore_backend.Repository;

import com.example.bookstore_backend.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {
    UserAuth findByUserid(@Param("userid")Integer userid);
    UserAuth findByUseridAndToken(@Param("userid") Integer userid, @Param("token")String token);

    List<UserAuth> findAllByType(@Param("type") Integer type);
}
