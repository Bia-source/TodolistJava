package com.beatriz.todolist.src.controller;

import com.beatriz.todolist.src.models.TaskModel;
import com.beatriz.todolist.src.repositories.TaskModelRepository;
import com.beatriz.todolist.src.shared.Priority;
import com.beatriz.todolist.src.shared.Time;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PutMapping("/time")
    public ResponseEntity startTask(@RequestBody TaskModel task, @RequestParam("time") Time time){
        if (time == Time.START) {
            var getTask = this.taskModelRepository.findById(task.getId());
            if (getTask.get().getStartAt() != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Uma data de inicio já foi definida");
            }

            getTask.get().setStartAt(LocalDateTime.now());
            this.taskModelRepository.save(getTask.get());
            return ResponseEntity.status(HttpStatus.OK).body(getTask);
        }

        if (time == Time.END){
            var getTask = this.taskModelRepository.findById(task.getId());
            if (getTask.get().getEndAt() != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Uma data de fim já foi definida");
            }

            getTask.get().setEndAt(LocalDateTime.now());
            getTask.get().setStatus(true);

            this.taskModelRepository.save(getTask.get());
            return ResponseEntity.status(HttpStatus.OK).body(getTask);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refaça a operação com valores validos (START, END)");
    }

    @PutMapping("/priority")
    public ResponseEntity updatePriority(@RequestBody TaskModel task, @RequestParam("priority") Priority priorityParam){
        var getTask = this.taskModelRepository.findById(task.getId());
        getTask.get().setPriority(priorityParam);

        this.taskModelRepository.save(getTask.get());
        return ResponseEntity.status(HttpStatus.OK).body(getTask);
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
