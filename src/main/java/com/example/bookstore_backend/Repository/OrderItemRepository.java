package com.example.bookstore_backend.Repository;

import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findAllByOrder(@Param("order" ) Order order);

    List<OrderItem> findAll();
    @Query(value="SELECT * FROM orderitems o WHERE o.title LIKE %:keyword% AND", nativeQuery = true)
    List<OrderItem> findAllByKeyword(@Param("keyword") String keyword);

    @Query(value="SELECT * FROM orderitems o WHERE o.title LIKE %:keyword% AND o.orderid IN (SELECT orderid FROM orders WHERE userid = :userid)", nativeQuery = true)
    List<OrderItem> filterUserKeyword(@Param("keyword") String keyword, @Param("userid") int userid);


}
