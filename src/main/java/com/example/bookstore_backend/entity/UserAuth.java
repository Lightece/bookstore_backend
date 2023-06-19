package com.example.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "userauth")
@Getter
@Setter
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid",referencedColumnName = "userid")
    private User user;

    private String password;
    private String token;
    private Integer type;
    public UserAuth() {
    }
}
