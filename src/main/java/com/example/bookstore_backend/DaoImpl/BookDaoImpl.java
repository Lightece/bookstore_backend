package com.example.bookstore_backend.DaoImpl;

import com.example.bookstore_backend.Dao.BookDao;
import com.example.bookstore_backend.Repository.BookRepository;
import com.example.bookstore_backend.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findByBookid(Integer bookid) {
        return bookRepository.findByBookid(bookid);
    }
    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Override
    public void delete(Book book){
        bookRepository.delete(book);
    }

    @Override
    public void save(Book book){
        bookRepository.save(book);
    }

}
