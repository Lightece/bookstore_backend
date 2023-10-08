package com.example.bookstore_backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cartitems", schema = "bookstore", catalog = "")
public class CartItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int itemid;
    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookid")
    private Book book;
    private int quantity;

    public CartItem() {
    }

}
