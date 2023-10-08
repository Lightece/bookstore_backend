package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.Dao.*;
import com.example.bookstore_backend.entity.*;
import com.example.bookstore_backend.model.Cart2Order;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Msg submitOrder(int userid, String address, String phone, String receiver, List<CartItem> CartItemList) {
        if(CartItemList.size() == 0){
            return new Msg("No item selected!", false, null);
        }
        else{
            // send message(json) to kafka
            Cart2Order cart2Order = new Cart2Order(userid, address, phone, receiver, CartItemList);
            ObjectMapper mapper = new ObjectMapper();
            String message = null;
            try {
                message = mapper.writeValueAsString(cart2Order);
            } catch (Exception e) {
                e.printStackTrace();
            }
            kafkaTemplate.send("buy", message);
            return new Msg("Submit order successfully!", true, null);
        }
    }

    public Msg getCartList(int userid){
        User user = userDao.getUserByUserid(userid);
        List<CartItem> cartItemList = cartDao.findAllByUser(user);
        if(cartItemList == null){
            return new Msg("Cart list is empty!", false, null);
        }
        else{
            return new Msg("Get cart list successfully!", true, cartItemList);
        }
    }

    public Msg updateCartItem(Integer userid, Integer bookid, Integer quantity) {
        User user = userDao.getUserByUserid(userid);
        Book book = bookDao.findByBookid(bookid);
        CartItem cartItem = cartDao.findByUserAndBook(user, book);
        if(cartItem == null)cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartDao.save(cartItem);
        return new Msg("Update cart item successfully!", true, null);
    }

    public Msg removeCartItem(Integer userid, Integer bookid) {
        User user = userDao.getUserByUserid(userid);
        Book book = bookDao.findByBookid(bookid);
        CartItem cartItem = cartDao.findByUserAndBook(user, book);
        if(cartItem == null){
            return new Msg("Cart item not found!", false, null);
        }
        else{
            cartDao.delete(cartItem);
            return new Msg("Remove cart item successfully!", true, null);
        }
    }
}
