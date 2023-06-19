package com.example.bookstore_backend.Repository;

import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    public List<OrderItem> findAllByOrder(@Param("order" ) Order order);
}
