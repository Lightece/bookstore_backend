package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.Dao.CartDao;
import com.example.bookstore_backend.Dao.OrderDao;
import com.example.bookstore_backend.Dao.UserDao;
import com.example.bookstore_backend.entity.CartItem;
import com.example.bookstore_backend.entity.Order;
import com.example.bookstore_backend.entity.OrderItem;
import com.example.bookstore_backend.entity.User;
import com.example.bookstore_backend.model.Cart2Order;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Msg getOrderList(int userid, String keyword, String startDate, String endDate) {
        User user = userDao.getUserByUserid(userid);
        List<Order> orderList = orderDao.findFilteredOrders(user, keyword, startDate, endDate);
        return new Msg("Get order list successfully!", true, orderList);
    }

    public Msg updateOrder(Order order) {
        orderDao.saveOrder(order);
        return new Msg("Update order successfully!", true, null);
    }

    public Msg getAllOrders(String keyword, String startDate, String endDate) {
        List<Order> orderList = orderDao.findFilteredOrders(null, keyword, startDate, endDate);
        return new Msg("Get order list successfully!", true, orderList);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class , isolation = Isolation.READ_COMMITTED)
    public Msg submitOrder(Cart2Order cart2Order) {
        // create new order
        User user = userDao.getUserByUserid(cart2Order.getUserid());
        Order order = new Order();
        order.setUser(user);
        order.setAddress(cart2Order.getAddress());
        order.setReceiver(cart2Order.getReceiver());
        order.setPhone(cart2Order.getPhone());
        order.setOrderdate(new Date(System.currentTimeMillis()));   // get current time
        order.setOrderstate("待发货");
        List<OrderItem> items = new ArrayList<>();
        double total = 0;
        // add items to order
        for (CartItem item : cart2Order.getCartItemList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(item.getBook());
            orderItem.setQuantity(item.getQuantity());
            items.add(orderItem);
            total += item.getBook().getPrice() * item.getQuantity();
            cartDao.delete(item);
        }
        order.setItems(items);
        order.setTotal(total);
        order = orderDao.saveOrder(order);
        for (OrderItem orderitem : items) {
            orderitem.setOrder(order);
            orderDao.saveOrderItem(orderitem);
        }
        ObjectMapper mapper = new ObjectMapper();
        Msg msg = new Msg("Order generated successfully!", true, order);
        String message = null;
        try {
            message = mapper.writeValueAsString(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Order generated: " + message);
        kafkaTemplate.send("order", message);
        return msg;
    }
}
