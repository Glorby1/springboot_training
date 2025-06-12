package com.example.demo.controller;

import com.example.demo.dto.TaskRequestDTO;
import com.example.demo.dto.TaskResponseDTO;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasksDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        return taskService.getTaskDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksDtoByUser(userId));
    }
    
    @GetMapping("/me")
    public ResponseEntity<List<TaskResponseDTO>> getMyTasks() {
        return ResponseEntity.ok(taskService.getMyTasks());
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO dto) {
        return ResponseEntity.ok(taskService.createTaskFromDto(dto));
    }


	@PutMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO dto) {
	    return ResponseEntity.ok(taskService.updateTaskFromDto(id, dto));
	}
	
	@PutMapping("me/{id}/done")
	public ResponseEntity<TaskResponseDTO> markTaskAsDone(@PathVariable Long id) {
	    return ResponseEntity.ok(taskService.updateTaskDoneStatus(id, true));
	}

	@PutMapping("me/{id}/notdone")
	public ResponseEntity<TaskResponseDTO> markTaskAsNotDone(@PathVariable Long id) {
	    return ResponseEntity.ok(taskService.updateTaskDoneStatus(id, false));
	}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
