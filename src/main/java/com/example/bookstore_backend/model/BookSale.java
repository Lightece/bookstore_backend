package com.example.bookstore_backend.model;

import com.example.bookstore_backend.entity.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSale {
    private Book book;
    private Integer sales;
    private Double total;

    public BookSale(Book book, Integer sales, Double total){
        this.book = book;
        this.sales = sales;
        this.total = total;
    }
}
