package com.beatriz.todolist.controller;

import com.beatriz.todolist.models.TaskModel;
import com.beatriz.todolist.repositories.TaskModelRepository;
import com.beatriz.todolist.shared.Time;
import com.beatriz.todolist.utils.Utils;
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

    @PutMapping("/{id}")
    public ResponseEntity updateTask(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
        var task = this.taskModelRepository.findById(id).orElse(null);
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");
        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse usuario não tem permissão para alterar tarefa de outro usuario");
        }

        Utils.copyNonNullProperties(taskModel, task);
        return ResponseEntity.status(HttpStatus.OK).body(this.taskModelRepository.save(task));
    }

    @PutMapping("/time/{id}")
    public ResponseEntity startTask(@PathVariable UUID id, @RequestParam("time") Time time){
        if (time == Time.START) {
            var task = this.taskModelRepository.findById(id);
            if (task.get().getStartAt() != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Uma data de inicio já foi definida");
            }

            task.get().setStartAt(LocalDateTime.now());
            this.taskModelRepository.save(task.get());
            return ResponseEntity.status(HttpStatus.OK).body(task);
        }

        if (time == Time.END){
            var task = this.taskModelRepository.findById(id);
            if (task.get().getEndAt() != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Uma data de fim já foi definida");
            }

            task.get().setEndAt(LocalDateTime.now());
            task.get().setStatus(true);

            this.taskModelRepository.save(task.get());
            return ResponseEntity.status(HttpStatus.OK).body(task);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refaça a operação com valores validos (START, END)");
    }

    @GetMapping("/title")
    public ResponseEntity getTaskByTitle(@RequestBody TaskModel task){
        return ResponseEntity.status(HttpStatus.OK).body(this.taskModelRepository.findTaskByTitle(task.getTitle()));
    }

    @GetMapping("/id")
    public ResponseEntity getTaskByStatus(@RequestBody TaskModel task){
        return ResponseEntity.status(HttpStatus.OK).body(this.taskModelRepository.findById(task.getId()));
    }

    @GetMapping("/user")
    public  ResponseEntity getTaskByIdUser(HttpServletRequest request){
        var task = this.taskModelRepository.findTaskByIdUser((UUID) request.getAttribute("idUser"));
        return  ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("/id")
    public ResponseEntity deleteTaskById(HttpServletRequest request){
        this.taskModelRepository.deleteById((UUID) request.getAttribute("idUser"));
        return ResponseEntity.status(HttpStatus.OK).body("delete");
    }
}
