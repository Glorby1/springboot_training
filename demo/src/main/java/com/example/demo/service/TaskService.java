package com.example.demo.service;

import com.example.demo.dto.TaskRequestDTO;
import com.example.demo.dto.TaskResponseDTO;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository; 

    
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> getAllTasksDto() {
        return taskRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }
    public Optional<TaskResponseDTO> getTaskDtoById(Long id) {
        return taskRepository.findById(id).map(this::mapToDto);
    }

	
    public List<TaskResponseDTO> getTasksDtoByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return taskRepository.findByAssignedTo(user).stream()
                .map(this::mapToDto)
                .toList();
    }

    public TaskResponseDTO createTaskFromDto(TaskRequestDTO dto) {
        User user = null;
        if (dto.getAssignedToId() != null) {
            user = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .done(dto.isDone())
                .assignedTo(user)
                .build();

        Task savedTask = taskRepository.save(task);

        return new TaskResponseDTO(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.isDone(),
                savedTask.getAssignedTo() != null ? savedTask.getAssignedTo().getId() : null
        );
    }

    public TaskResponseDTO updateTaskFromDto(Long id, TaskRequestDTO dto) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(dto.getTitle());
                    task.setDescription(dto.getDescription());
                    task.setDone(dto.isDone());

                    if (dto.getAssignedToId() != null) {
                        User user = userRepository.findById(dto.getAssignedToId())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                        task.setAssignedTo(user);
                    } else {
                        task.setAssignedTo(null);
                    }

                    return mapToDto(taskRepository.save(task));
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    public TaskResponseDTO updateTaskDoneStatus(Long id, boolean done) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        task.setDone(done);
        Task updated = taskRepository.save(task);

        return mapToDto(updated);
    }

    private TaskResponseDTO mapToDto(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isDone(),
                task.getAssignedTo() != null ? task.getAssignedTo().getId() : null
        );
    }
}
