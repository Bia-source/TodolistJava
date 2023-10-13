package com.beatriz.todolist.src.repositories;

import com.beatriz.todolist.src.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.ArrayList;
import java.util.UUID;

public interface TaskModelRepository extends JpaRepository<TaskModel, UUID> {
    TaskModel findTaskByTitle(String title);
    ArrayList<TaskModel> findTaskByIdUser(UUID idUser);
}
