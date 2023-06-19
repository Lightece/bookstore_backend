package com.example.bookstore_backend.Dao;

import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.entity.CartItem;
import com.example.bookstore_backend.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao {
    List<CartItem> findAllByUser(@Param("user") User user);

    CartItem findByUserAndBook(@Param("user") User user, @Param("book") Book book);

    void save(@Param("cartItem") CartItem cartItem);

    void delete(@Param("cartItem") CartItem cartItem);
}
