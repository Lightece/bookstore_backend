package com.example.bookstore_backend.service;

import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.model.Msg;

public interface OrderService {
    Msg getOrderList(int userid,String keyword, String startDate, String endDate);
    Msg updateOrder(Order order);
    Msg getAllOrders(String keyword, String startDate, String endDate);
}
