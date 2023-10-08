package com.example.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "orders", schema = "bookstore", catalog = "")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderid;
    private String address;
    private String phone;
    private Double total;
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;
    private Date orderdate;
    private String receiver;
    private String orderstate;
    @OneToMany
    @Transient
    @JsonProperty
    private List<OrderItem> items;
    public Order() {
    }

    public void setOrderItems(List<OrderItem> items_to_set) {
        items = items_to_set;
    }
}
