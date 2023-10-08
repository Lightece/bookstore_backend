package com.example.bookstore_backend.model;

import com.example.bookstore_backend.entity.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Cart2Order{
    int userid;
    String address;
    String phone;
    String receiver;
    List<CartItem> cartItemList;
    public Cart2Order() {
    }
    public Cart2Order(int userid,String address,String phone,String receiver,List<CartItem> cartItemList) {
        this.userid = userid;
        this.address = address;
        this.phone = phone;
        this.receiver = receiver;
        this.cartItemList = cartItemList;
    }
}
