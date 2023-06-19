package com.example.bookstore_backend.service;

import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.model.Msg;

public interface OrderService {
    Msg getOrderList(int userid);
    Msg updateOrder(Order order);

}
