package com.example.bookstore_backend.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cartitems", schema = "bookstore", catalog = "")
public class CartItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int itemid;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookid")
    private Book book;
    private int quantity;

    public CartItem() {
    }

}
