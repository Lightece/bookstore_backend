package com.example.bookstore_backend.model;

import com.example.bookstore_backend.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBuy {
    private User user;
    private Double total;

    public UserBuy(User user, Double total) {
        this.user = user;
        this.total = total;
    }
}
