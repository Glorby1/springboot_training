package com.example.demo.dto;

public class TaskRequestDTO{
    private String title;
    private String description;
    private boolean done;
    private Long assignedToId; // uniquement l'id du user

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }
}
