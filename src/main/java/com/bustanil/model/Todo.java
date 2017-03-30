package com.bustanil.model;

import javax.persistence.*;

@Entity
public class Todo {
    public enum Filter {
        ALL, ACTIVE, COMPLETED
    }

    private Long id;
    private String task;
    private Boolean completed = false;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
