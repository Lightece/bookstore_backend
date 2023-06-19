package com.example.bookstore_backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "books", schema = "bookstore", catalog = "")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookid;
    private String title;
    private String author;
    private double price;
    private String isbn;
    private Integer stock;
    private String cover;

    public Book() {
    }

    public Book(String title, String author, double price, String isbn, Integer stock, String cover) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
        this.stock = stock;
        this.cover = cover;
    }
}
