package com.example.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users", schema = "bookstore", catalog = "")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;


    private String nickname;

    private String tel;
    private String address;

    private String email;

    private String token;

    private String avatar;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    @JsonIgnore
    @Transient
    private List<Order> orders;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    @JsonIgnore
    @Transient
    private List<CartItem> cartItemList;
}