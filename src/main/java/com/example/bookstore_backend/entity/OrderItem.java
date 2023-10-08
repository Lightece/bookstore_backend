package com.example.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "orderitems", schema = "bookstore", catalog = "")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemid;
    @ManyToOne
    @JoinColumn(name = "bookid")
    private Book book;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orderid")
    private Order order;
    private Integer quantity;
}
