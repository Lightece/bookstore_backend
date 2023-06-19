package com.example.bookstore_backend.DaoImpl;

import com.example.bookstore_backend.Dao.OrderDao;
import com.example.bookstore_backend.Repository.OrderItemRepository;
import com.example.bookstore_backend.Repository.OrderRepository;
import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.entity.OrderItem;
import com.example.bookstore_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Order> findAllByUser(@Param("user") User user){
        List<Order> list = orderRepository.findAllByUser(user);
        for(Order order : list){
            order.setOrderItems(orderItemRepository.findAllByOrder(order));
        }
        return list;
    }

    @Override
    public List<OrderItem> getItemsByOrder(@Param("order" ) Order order){
        return orderItemRepository.findAllByOrder(order);
    }

    @Override
    public Order saveOrder(@Param("order") Order order){
        return orderRepository.save(order);
    }

    @Override
    public void saveOrderItem(@Param("orderItem") OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }
}
