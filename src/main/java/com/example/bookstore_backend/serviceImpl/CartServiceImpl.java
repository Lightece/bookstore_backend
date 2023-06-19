package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.Dao.*;
import com.example.bookstore_backend.entity.*;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;


    public Msg submitOrder(int userid, String address, String phone, String receiver, List<CartItem> CartItemList) {
        System.out.print("submitOrder");
        System.out.print("\tuserid=");System.out.print(userid);
        System.out.print("\taddress=");System.out.print(address);
        System.out.print("\tphone=");System.out.print(phone);
        System.out.print("\treceiver=");System.out.println(receiver);
        if(CartItemList.size() == 0){
            return new Msg("No item selected!", false, null);
        }
        else{
            // create new order
            User user = userDao.getUserByUserid(userid);
            Order order = new Order();
            order.setUser(user);order.setAddress(address);order.setReceiver(receiver);order.setPhone(phone);
            order.setOrderdate(new Date(System.currentTimeMillis()));   // get current time
            order.setOrderstate("待发货");
            List<OrderItem> items = new ArrayList<>();
            double total = 0;
            // add items to order and delete them from cart
            for(CartItem item : CartItemList){
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
            for(OrderItem orderitem : items){
                orderitem.setOrder(order);
                orderDao.saveOrderItem(orderitem);
            }
            return new Msg("Submit order successfully!", true, order);
        }
    }

    public Msg getCartList(int userid){
        User user = userDao.getUserByUserid(userid);
        List<CartItem> cartItemList = cartDao.findAllByUser(user);
        if(cartItemList == null){
            return new Msg("Cart list is empty!", false, null);
        }
        else{
            return new Msg("Get cart list successfully!", true, cartItemList);
        }
    }

    public Msg updateCartItem(Integer userid, Integer bookid, Integer quantity) {
        User user = userDao.getUserByUserid(userid);
        Book book = bookDao.findByBookid(bookid);
        CartItem cartItem = cartDao.findByUserAndBook(user, book);
        if(cartItem == null)cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartDao.save(cartItem);
        return new Msg("Update cart item successfully!", true, null);
    }

    public Msg removeCartItem(Integer userid, Integer bookid) {
        User user = userDao.getUserByUserid(userid);
        Book book = bookDao.findByBookid(bookid);
        CartItem cartItem = cartDao.findByUserAndBook(user, book);
        if(cartItem == null){
            return new Msg("Cart item not found!", false, null);
        }
        else{
            cartDao.delete(cartItem);
            return new Msg("Remove cart item successfully!", true, null);
        }
    }
}
