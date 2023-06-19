package com.example.bookstore_backend.Dao;


import com.example.bookstore_backend.entity.Book;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao {
    Book findByBookid(@Param("bookid") Integer bookid);
    List<Book> findAll();
    void delete(Book book);

    void save(Book book);
}
