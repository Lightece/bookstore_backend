package com.example.bookstore_backend.service;

import com.example.bookstore_backend.entity.Book;
import com.example.bookstore_backend.model.Msg;

public interface BookService {
    Msg getBookById(Integer id);
    Msg getBookList(String keyword);
    Msg deleteBook(Book book);
    Msg updateBook(Book book);

    Msg getValidBooks(String searchType, String searchValue);

    Msg getBookSales(String startDate, String endDate);

    Msg getBoughtBooks(Integer userid, String startDate, String endDate);
}
