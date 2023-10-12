package com.beatriz.todolist.user.repositories;

import com.beatriz.todolist.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserModelRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);

}