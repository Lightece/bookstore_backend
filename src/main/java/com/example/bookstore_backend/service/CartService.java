package com.example.bookstore_backend.service;

import com.example.bookstore_backend.entity.CartItem;
import com.example.bookstore_backend.model.Msg;

import java.util.List;

public interface CartService {
    Msg submitOrder(int userid, String address, String phone, String receiver, List<CartItem> CartItemList);
    Msg getCartList(int userid);
    Msg updateCartItem(Integer userid, Integer bookid, Integer num);
    Msg removeCartItem(Integer userid, Integer bookid);
}
