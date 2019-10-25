package com.example.profile2;

import java.io.Serializable;

public class User implements Serializable {
    String username,Name,phone;
    int Atype;
    public User(){

    }

    public User(String username, String name, String phone, int atype) {
        this.username = username;
        this.Name = name;
        this.phone = phone;
        this.Atype = atype;
    }
}
