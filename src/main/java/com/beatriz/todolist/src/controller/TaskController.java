package com.beatriz.todolist.src.controller;

import com.beatriz.todolist.src.models.TaskModel;
import com.beatriz.todolist.src.repositories.TaskModelRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskModelRepository taskModelRepository;

    @PostMapping("")
    public ResponseEntity createTask(@RequestBody TaskModel task, HttpServletRequest request){
        if(task.getDescription() == null || task.getTitle() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possivel cadastrar, verifique as informações");
        }

        task.setIdUser((UUID) request.getAttribute("idUser"));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.taskModelRepository.save(task));
    }

    @GetMapping("/title")
    public ResponseEntity getTaskByTitle(@RequestBody TaskModel taskModel){
        return ResponseEntity.status(HttpStatus.OK).body(this.taskModelRepository.findTaskByTitle(taskModel.getTitle()));
    }

    @GetMapping("/id")
    public ResponseEntity getTaskByStatus(@RequestBody TaskModel taskModel){
        return ResponseEntity.status(HttpStatus.OK).body(this.taskModelRepository.findById(taskModel.getId()));
    }

    @GetMapping("/user")
    public  ResponseEntity getTaskByIdUser(@RequestBody TaskModel taskModel){
        var task = this.taskModelRepository.findTaskByIdUser(taskModel.getIdUser());
        System.out.println(task);
        return  ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("/id")
    public ResponseEntity deleteTaskById(@RequestBody TaskModel taskModel){
        this.taskModelRepository.deleteById(taskModel.getId());
        return ResponseEntity.status(HttpStatus.OK).body("delete");
    }
}
