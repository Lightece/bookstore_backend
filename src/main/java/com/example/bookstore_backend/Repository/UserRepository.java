package com.example.bookstore_backend.Repository;

import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
     User findByUserid(@Param("userid") Integer userid);
}



