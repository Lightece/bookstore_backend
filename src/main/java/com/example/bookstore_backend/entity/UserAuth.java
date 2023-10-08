package com.example.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "userauth")
@Getter
@Setter
public class UserAuth {
    @Id
    private Integer userauthid;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userauthid", referencedColumnName = "userid")
    private User user;


    private String password;
//    private String token;
    private Integer type;
    public UserAuth() {
    }

    public UserAuth(Integer userauthid, String password, int type) {
        this.userauthid = userauthid;
        this.password = password;
        this.type = type;
//        this.token = null;
    }
    public UserAuth(Integer userauthid, String password, int type,String token) {
        this.userauthid = userauthid;
        this.password = password;
        this.type = type;
//        this.token = token;
    }
}
