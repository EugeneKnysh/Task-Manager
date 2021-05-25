package com.gmail.eugeneknysh21.taskmanager.controllers;

import com.gmail.eugeneknysh21.taskmanager.dto.TaskDTO;
import com.gmail.eugeneknysh21.taskmanager.repositories.projection.TasksCount;
import com.gmail.eugeneknysh21.taskmanager.services.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    private final TaskService taskService;

    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task/add")
    public Long addTask(@Valid @RequestBody TaskDTO taskDTO) {
        return taskService.addTask(taskDTO);
    }

    @DeleteMapping("/task/delete")
    public void deleteTask(@RequestParam Long id) {
        taskService.removeTask(id);
    }

    @GetMapping("/tasks/count/month")
    public List<TasksCount> getCountOnMonth(@RequestParam int y,
                                            @RequestParam int m) {
        return taskService.getCountTasksByDayOnMonth(y, m);
    }

    @GetMapping("/tasks/list/day")
    public List<TaskDTO> getTasksForDay(@RequestParam int y,
                                        @RequestParam int m,
                                        @RequestParam int d) {
        return taskService.getTasksForDay(LocalDate.of(y, m, d));
    }
}
