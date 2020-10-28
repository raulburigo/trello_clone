package org.raulburigo.models;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "activities")
public class Activity extends PanacheEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;    

    @Column(nullable = false, updatable = false)
    private String username;

    private ActivityType type;

    @ManyToOne(optional = false)
    @JsonbTransient
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Activity(Todo todo, String username, ActivityType type) {
        this.username = username;
        this.type = type;
        this.todo = todo;
    }

    public Activity() {
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return this.type.getActivityText();
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Todo getTodo() {
        return this.todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

}
