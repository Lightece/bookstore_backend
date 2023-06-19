package com.example.bookstore_backend.service;

import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.model.Msg;

public interface BookService {
    Msg getBookById(Integer id);
    Msg getBookList();
    Msg deleteBook(Book book);
    Msg updateBook(Book book);
}
