package com.beatriz.todolist.user.controller;

import com.beatriz.todolist.user.models.UserModel;
import com.beatriz.todolist.user.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserModelRepository userRepository;

    @GetMapping("")
    public String getUser(){
        return "todos";
    }

    @PostMapping("")
    public UserModel create(@RequestBody UserModel userModel){
        var newUser = this.userRepository.save(userModel);
        System.out.println(newUser);
        return newUser;
    }
}
