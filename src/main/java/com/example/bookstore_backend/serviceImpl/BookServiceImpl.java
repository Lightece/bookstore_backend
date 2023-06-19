package com.example.bookstore_backend.serviceImpl;

import com.example.bookstore_backend.Dao.BookDao;
import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.model.Msg;
import com.example.bookstore_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;
    @Override
    public Msg getBookById(Integer id){
        Book book = bookDao.findByBookid(id);
        if(book == null){
            return new Msg("Book does not exist!", false, null);
        }
        else{
            return new Msg("Get book successfully!", true, book);
        }
    }
    @Override
    public Msg getBookList(){
        List<Book> bookList = bookDao.findAll();
        if(bookList == null){
            return new Msg("Book list is empty!", false, null);
        }
        else{
            return new Msg("Get book list successfully!", true, bookList);
        }
    }

    @Override
    public Msg deleteBook(Book book) {
        bookDao.delete(book);
        return new Msg("Delete book successfully!", true, null);
    }
    @Override
    public Msg updateBook(Book book) {
        bookDao.save(book);
        return new Msg("Update book successfully!", true, null);
    }
}
