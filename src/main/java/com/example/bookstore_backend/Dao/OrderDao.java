package com.example.bookstore_backend.Dao;

import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.entity.OrderItem;
import com.example.bookstore_backend.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {
    List<Order> findAllByUser(@Param("user") User user);
    List<OrderItem> getItemsByOrder(@Param("order" ) Order order);

    Order saveOrder(@Param("order") Order order);

    void saveOrderItem(@Param("orderItem") OrderItem orderItem);
}
