package com.example.bookstore_backend.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

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
    private int status; // 0: normal, 1: deleted

    public Book() {
    }

    public Book(String title, String author, double price, String isbn, Integer stock, String cover, int status) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
        this.stock = stock;
        this.cover = cover;
        this.status = status;
    }
}
