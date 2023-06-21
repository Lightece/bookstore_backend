package com.example.bookstore_backend.Dao;

import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.entity.OrderItem;
import com.example.bookstore_backend.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {
    List<Order> findFilteredOrders(@Param("user") User user,@Param("keyword")String keyword,@Param("startDate") String startDate,@Param("endDate") String endDate);

    List<OrderItem> getItemsByOrder(@Param("order" ) Order order);
    List<OrderItem> findAllOrderItems();
    List<OrderItem> findAllOrderItemsByUser(@Param("user") User user);

    Order saveOrder(@Param("order") Order order);

    void saveOrderItem(@Param("orderItem") OrderItem orderItem);



}
