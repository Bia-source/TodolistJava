package com.beatriz.todolist.user.models;

import lombok.Data;

@Data
public class UserModel {
    private String name;
    private String username;
    private String password;

    public UserModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserModel(){
    }
}

