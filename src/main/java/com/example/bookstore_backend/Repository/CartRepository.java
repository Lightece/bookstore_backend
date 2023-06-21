package com.example.bookstore_backend.Repository;

import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.entity.CartItem;
import com.example.bookstore_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByUser(@Param("user") User user);

    CartItem findByUserAndBook(@Param("user") User user, @Param("book") Book book);
}
