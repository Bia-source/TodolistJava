package com.beatriz.todolist.src.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.beatriz.todolist.src.models.UserModel;
import com.beatriz.todolist.src.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserModelRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity getUser(){
        return ResponseEntity.status(HttpStatus.OK).body(this.userRepository.findAll());
    }

    @GetMapping("/username")
    public ResponseEntity getUserByUsername(@RequestBody UserModel user){
        return ResponseEntity.status(HttpStatus.OK).body(this.userRepository.findByUsername(user.getUsername()));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var alreadyExist = this.userRepository.findByUsername(userModel.getUsername());
        if(alreadyExist != null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe");
        }
        var passwordHash = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHash);
        var newUser = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/id")
    public ResponseEntity deleteUserById(@RequestBody UserModel userModel){
        this.userRepository.deleteById(userModel.getId());
        return ResponseEntity.status(HttpStatus.OK).body("delete");
    }


}
