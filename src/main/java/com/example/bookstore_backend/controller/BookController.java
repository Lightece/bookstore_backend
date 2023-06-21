package com.example.bookstore_backend.controller;
/*
BookController:
    * getBookById OK
    * updateBook  OK
    * getBookList
 */
import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.service.BookService;
import com.example.bookstore_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "localhost:3000")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @GetMapping("/getBookById")
    public Msg getBookById(@RequestParam("id") int id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/updateBook")
    public Msg updateBook(@RequestBody  Map<String, Object> params){
        Integer userid = Integer.parseInt((String) params.get("userid"));
        String token = (String)params.get("token");
        if(!userService.checkAdmin(userid, token).isOk()) return new Msg("User is not admin!", false, null);
        Map<String, Object> book_prop = (Map<String, Object>)params.get("book");
        Book book = new Book();
        if(book_prop.get("bookid") != null) book.setBookid((Integer)book_prop.get("bookid"));
        book.setTitle((String)book_prop.get("title"));
        book.setAuthor((String)book_prop.get("author"));
        book.setPrice(Double.parseDouble((String)book_prop.get("price")));
        book.setIsbn((String)book_prop.get("isbn"));
        book.setStock((Integer)book_prop.get("stock"));
        book.setCover((String)book_prop.get("cover"));
        return bookService.updateBook(book);
    }

    @PostMapping("/setBookStatus")
    public Msg setBookStatus(@RequestBody  Map<String, Object> params){
        Integer userid = Integer.parseInt((String) params.get("userid"));
        String token = (String)params.get("token");
        if(!userService.checkAdmin(userid, token).isOk()) return new Msg("User is not admin!", false, null);
        Integer bookid = (Integer) params.get("bookid");
        Integer status = (Integer) params.get("status");
        Book book = (Book)bookService.getBookById(bookid).getData();
        if(book == null) return new Msg("Book doesn't exist!", false, null);
        book.setStatus(status);
        return bookService.updateBook(book);
    }

    @PostMapping("/getBookList")
    public Msg getBookList(@RequestBody Map<String, Object> Params) {
        Integer userid = Integer.parseInt((String)Params.get("userid"));
        String token = (String)Params.get("token");
        if(!userService.checkAdmin(userid, token).isOk()) return new Msg("User is not admin!", false, null);
        String keyword = (String)Params.get("keyword");
        return bookService.getBookList(keyword);
    }

    @GetMapping("/getValidBooks")
    public Msg getValidBooks(@RequestParam("type") String searchType, @RequestParam("value") String searchValue) {
        return bookService.getValidBooks(searchType, searchValue);
    }

    @PostMapping("/getBookSales")
    public Msg getBookSales(@RequestBody Map<String, Object> Params) {
        Integer userid = Integer.parseInt((String)Params.get("userid"));
        String token = (String)Params.get("token");
        if(!userService.checkAdmin(userid, token).isOk()) return new Msg("User is not admin!", false, null);
        String startDate = (String)Params.get("startDate");
        String endDate = (String)Params.get("endDate");
        return bookService.getBookSales(startDate, endDate);
    }
    @PostMapping("/getBoughtBooks")
    public Msg getBuyedBooks(@RequestBody Map<String, Object> Params) {
        Integer userid = Integer.parseInt((String)Params.get("userid"));
        String token = (String)Params.get("token");
        if(!userService.CheckUserStatus(userid, token).isOk()) return new Msg("User is not logged in!", false, null);
        String startDate = (String)Params.get("startDate");
        String endDate = (String)Params.get("endDate");
        return bookService.getBoughtBooks(userid, startDate, endDate);
    }
}

