package com.beatriz.todolist.user.controller;

import com.beatriz.todolist.user.models.UserModel;
import com.beatriz.todolist.user.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity create(@RequestBody UserModel userModel){
        var alreadyExist = this.userRepository.findByUsername(userModel.getUsername());
        if(alreadyExist != null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe");
        }
        var newUser = this.userRepository.save(userModel);
        System.out.println(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
