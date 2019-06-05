package com.imooc.bootshiro.model;

import lombok.Data;

@Data
public class UserModel {

    private String userName;

    private String password;

    public UserModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
