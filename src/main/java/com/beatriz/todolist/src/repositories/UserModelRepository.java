package com.beatriz.todolist.src.repositories;

import com.beatriz.todolist.src.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserModelRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);

}