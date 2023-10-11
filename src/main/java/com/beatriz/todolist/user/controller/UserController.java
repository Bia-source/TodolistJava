package com.beatriz.todolist.user.controller;

import com.beatriz.todolist.user.models.UserModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/users")
public class UserController {
//    public ArrayList<String> todo = [];
    @GetMapping("")
    public String getUser(){
        return "todos";
    }

    @PostMapping("")
    public UserModel create(@RequestBody UserModel userModel){
        System.out.println(userModel.getName());
        return userModel;
    }
}
