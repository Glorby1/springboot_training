package com.example.demo.dto;

public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private boolean done;
    private Long assignedToId;

    public TaskResponseDTO(Long id, String title, String description, boolean done, Long assignedToId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
        this.assignedToId = assignedToId;
    }

    public Long getId() {
        return id;
    }

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
}
