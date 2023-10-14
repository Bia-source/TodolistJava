package com.beatriz.todolist.todo.repositories;

import com.beatriz.todolist.todo.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.ArrayList;
import java.util.UUID;

public interface TaskModelRepository extends JpaRepository<TaskModel, UUID> {
    TaskModel findTaskByTitle(String title);
    ArrayList<TaskModel> findTaskByIdUser(UUID idUser);
}
