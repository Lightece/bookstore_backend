package com.example.bookstore_backend.DaoImpl;

import com.example.bookstore_backend.Dao.CartDao;
import com.example.bookstore_backend.Repository.CartRepository;
import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.entity.CartItem;
import com.example.bookstore_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private CartRepository cartRepository;
    @Override
    public List<CartItem> findAllByUser(@Param("user") User user){
        return cartRepository.findAllByUser(user);
    }

    @Override
    public CartItem findByUserAndBook(@Param("user") User user, @Param("book") Book book){
        return cartRepository.findByUserAndBook(user, book);
    }
    @Override
    public void save(@Param("cartItem") CartItem cartItem){
        cartRepository.save(cartItem);
    }

    @Override
    public void delete(@Param("cartItem") CartItem cartItem){
        cartRepository.delete(cartItem);
    }

}
