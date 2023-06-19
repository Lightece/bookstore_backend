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
        Map<String, Object> book_prop = (Map<String, Object>) params.get("book");
        Book book = new Book();
        if(book_prop.get("bookid") != null) book.setBookid((Integer)book_prop.get("bookid"));
        book.setTitle((String)book_prop.get("title"));
        book.setAuthor((String)book_prop.get("author"));
        book.setPrice((Double)book_prop.get("price"));
        book.setStock((Integer)book_prop.get("stock"));

        if(!userService.checkAdmin(userid, token).isOk()) return new Msg("User is not admin!", false, null);
        return bookService.updateBook(book);
    }

    @PostMapping("/deleteBook")
    public Msg deleteBook(@RequestBody  Map<String, Object> params){
        Integer userid = Integer.parseInt((String) params.get("userid"));
        String token = (String)params.get("token");
        if(!userService.checkAdmin(userid, token).isOk()) return new Msg("User is not admin!", false, null);
        Integer bookid = (Integer) params.get("bookid");
        Book book = (Book)bookService.getBookById(bookid).getData();
        if(book == null) return new Msg("Book doesn't exist!", false, null);
        return bookService.deleteBook(book);
    }

    @GetMapping("/getBookList")
    public Msg getBookList(){
        return bookService.getBookList();
    }
}

