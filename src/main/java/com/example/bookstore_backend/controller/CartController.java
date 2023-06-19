package com.example.bookstore_backend.controller;
/*
CartController: used to control all changes and post info of cart
functions:
- getCart: get the cart of a user  Tested
- addToCart: add selected books to the cart of a user   Tested
- deleteFromCart: delete selected books from the cart of a user  Tested
- buy: buy selected books in the cart of a user, then delete them  Tested
 */


import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.entity.CartItem;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.serviceImpl.BookServiceImpl;
import com.example.bookstore_backend.serviceImpl.CartServiceImpl;
import com.example.bookstore_backend.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class CartController {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private UserServiceImpl userService;


    @PostMapping("getCart")
    public Msg getCart(@RequestBody Map<String, Object> params){
        String token = (String) params.get("token");
        Integer userid = Integer.valueOf((String)params.get("userid"));
        System.out.print("getCart of User:");System.out.println(userid);

        if(!userService.CheckUserStatus(userid, token).isOk()){
            System.out.println("Failed to get cart: user not authorized");
            return new Msg("user not authorized",false, null);
        }
        return cartService.getCartList(userid);
    }


    @PostMapping("addToCart")
    public Msg addToCart(@RequestBody Map<String, Object> params){
        System.out.println("addToCart of User:"+params.get("userid")+" bookid:"+ params.get("bookid") +" quantity:"+params.get("quantity"));
        int userid = Integer.parseInt((String) params.get("userid"));
        int bookid = Integer.parseInt((String) params.get("bookid"));
        int quantity = (Integer) params.get("quantity");
        String token = (String)params.get("token");
        System.out.println("addToCart of User:"+userid+" bookid:"+bookid+" quantity:"+quantity+"token:"+token);
        // check if the user is authorized
        if(!userService.CheckUserStatus(userid, token).isOk()){
            System.out.println("Failed to add to cart: user not authorized");
            return new Msg("user not authorized",false, null);
        }
        System.out.println("addToCart of User:"+userid+" bookid:"+bookid+" quantity:"+quantity);
        return cartService.updateCartItem(userid, bookid, quantity);
    }

    @PostMapping("deleteFromCart")
    public Msg deleteFromCart(@RequestBody Map<String, Object> params){
        int userid = Integer.parseInt((String) params.get("userid"));
        int bookid = (Integer) params.get("bookid");
        String token = (String)params.get("token");
        System.out.println("TEST: deleteFromCart of User:"+userid+" bookid:"+bookid+" token:"+token);
        return cartService.removeCartItem(userid, bookid);
    }

    @PostMapping("updateCartItem")
    public Msg updateQuantity(@RequestBody Map<String, Object> params){
        Integer userid = Integer.parseInt((String) params.get("userid"));
        int bookid = (Integer)params.get("bookid");
        int quantity = (Integer) params.get("quantity");
        String token = (String)params.get("token");
        Msg msg = userService.CheckUserStatus(userid, token);
        if(!msg.isOk()){
            return msg;
        }
        return cartService.updateCartItem(userid, bookid, quantity);
    }


    @PostMapping("buy")
    public Msg buy(@RequestBody Map<String, Object> params) {
        Integer userid = Integer.valueOf((String) params.get("userid"));
        String token = (String) params.get("token");
        String address = (String) params.get("address");
        String phone = (String) params.get("phone");
        String receiver = (String) params.get("receiver");
        //TODO: check how to get list
        ObjectMapper mapper = new ObjectMapper();
        List<CartItem> tmp = (List<CartItem>) params.get("CartItems");
        List<CartItem> itemList= mapper.convertValue(tmp, new TypeReference<List<CartItem>>() { });
        System.out.println("TEST: buy of User:" + userid + " token:" + token + " bookList:" + itemList);

        // check if the user is authorized
        if (!userService.CheckUserStatus(userid, token).isOk()) {
            System.out.println("Failed to buy: user not authorized");
            return new Msg("user not authorized", false, null);
        }

        // check stock
        for (CartItem item : itemList) {
            if (item.getQuantity() > item.getBook().getStock()) {
                System.out.println("Failed to buy: stock not enough");
                return new Msg("stock not enough", false, null);
            }
        }
        // update stock
        for (CartItem item : itemList) {
            Book newBook = item.getBook();
            newBook.setStock(newBook.getStock() - item.getQuantity());
            bookService.updateBook(newBook);
        }
        // submit order
        return cartService.submitOrder(userid, address, phone, receiver, itemList);
    }
}
