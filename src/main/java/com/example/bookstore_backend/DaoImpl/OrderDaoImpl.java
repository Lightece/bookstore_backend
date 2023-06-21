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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;
import java.text.SimpleDateFormat;


@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private static Date parseDate(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utildate = format.parse(dateStr);
            java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findFilteredOrders(@Param("user") User user,@Param("keyword")String keyword,@Param("startDate") String startDate,@Param("endDate") String endDate) {
        List<Order> list = new ArrayList<>();
        if(user==null){
            list.addAll(orderRepository.findAll());
        }
        else list.addAll(orderRepository.findAllByUser(user));
        List<Order> filtered = new ArrayList<>();
        for(Order order : list){
            order.setOrderItems(orderItemRepository.findAllByOrder(order));
        }
        if(keyword!=""){
            for(Order order : list){
                for(OrderItem orderItem : order.getItems()){
                    if(orderItem.getBook().getTitle().contains(keyword)){
                        filtered.add(order);
                        break;
                    }
                }
            }
            list.clear();
            list.addAll(filtered);
            filtered.clear();
        }
        if(startDate!=null && startDate!="" && endDate!=null && endDate!=""){
            Date start = parseDate(startDate);
            Date end = parseDate(endDate);
            for(Order order : list){
                if(order.getOrderdate().compareTo(start)>=0 && order.getOrderdate().compareTo(end)<=0){
                    filtered.add(order);
                }
            }
            return filtered;
        }
        else if(startDate!=null && startDate!=""){
            Date start = parseDate(startDate);
            for(Order order : list){
                if(order.getOrderdate().compareTo(start)>=0 ){
                    filtered.add(order);
                }
            }
            return filtered;
        }
        else if(endDate!=null && endDate!=""){
            Date end = parseDate(endDate);
            for(Order order : list){
                if(order.getOrderdate().compareTo(end)<=0){
                    filtered.add(order);
                }
            }
            return filtered;
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

    public List<OrderItem> findAllOrderItems(){
        return orderItemRepository.findAll();
    }

    public List<OrderItem> findAllOrderItemsByUser(@Param("user") User user){
        List<Order> orders = orderRepository.findAllByUser(user);
        List<OrderItem> orderItems = new ArrayList<>();
        for(Order order : orders){
            order.setOrderItems(orderItemRepository.findAllByOrder(order));
            orderItems.addAll(order.getItems());
        }
        return orderItems;
    }
}
