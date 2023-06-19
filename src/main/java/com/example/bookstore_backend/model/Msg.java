package com.example.bookstore_backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Msg {
    private String message;
    private boolean ok;
    private Object data;

    Msg(){

    }

    public Msg(String message, boolean ok, Object data){
        this.message = message;
        this.ok = ok;
        this.data = data;
    }

}
