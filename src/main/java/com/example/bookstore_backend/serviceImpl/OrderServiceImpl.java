package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.Dao.CartDao;
import com.example.bookstore_backend.Dao.OrderDao;
import com.example.bookstore_backend.Dao.UserDao;
import com.example.bookstore_backend.Repository.CartRepository;
import com.example.bookstore_backend.Repository.OrderItemRepository;
import com.example.bookstore_backend.Repository.OrderRepository;
import com.example.bookstore_backend.Repository.UserRepository;
import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private UserDao userDao;

    public Msg getOrderList(int userid){
        User user = userDao.getUserByUserid(userid);
        List<Order> orderList = orderDao.findAllByUser(user);
        if(orderList == null){
            return new Msg("Order list is empty!", false, null);
        }
        else{
            return new Msg("Get order list successfully!", true, orderList);
        }
    }

    public Msg updateOrder(Order order) {
        orderDao.saveOrder(order);
        return new Msg("Update order successfully!", true, null);
    }

}
