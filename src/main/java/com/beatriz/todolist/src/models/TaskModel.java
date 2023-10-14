package com.beatriz.todolist.src.models;

import com.beatriz.todolist.src.shared.Priority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String title;
    private String description;
    private UUID idUser;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Column(name = "complete_status")
    private Boolean status = false;
    private Priority priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception{
        if(title.length() > 50){
            throw new Exception("Limite de caracteres ultrapassado");
        }
        this.title = title;
    }
}
