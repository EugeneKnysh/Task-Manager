package com.gmail.eugeneknysh21.taskmanager.services.impl;

import com.gmail.eugeneknysh21.taskmanager.dto.TaskDTO;
import com.gmail.eugeneknysh21.taskmanager.entity.Task;
import com.gmail.eugeneknysh21.taskmanager.repositories.TaskRepository;
import com.gmail.eugeneknysh21.taskmanager.repositories.projection.TasksCount;
import com.gmail.eugeneknysh21.taskmanager.services.TaskService;
import com.gmail.eugeneknysh21.taskmanager.utility.UtilClass;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private TaskDTO mapToTaskDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getDate(),
                task.getTime(),
                task.getText());
    }

    @Override
    @Transactional
    public Long addTask(TaskDTO taskDTO) {
        Task task = new Task(
                taskDTO.getDate(),
                taskDTO.getTime(),
                taskDTO.getText());
        return taskRepository.save(task).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::mapToTaskDTO)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " doesn`t exist."));
    }

    @Override
    @Transactional
    public void removeTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TasksCount> getCountTasksByDayOnMonth(Integer year, Integer month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }
        return taskRepository.getCountTasksByDayOnMonth(year.toString(),
                                                        UtilClass.convertMonthIntToString(month));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksForDay(LocalDate date) {
        return taskRepository.getAllByDateOrderByTimeAsc(date.toString())
                .stream()
                .map(this::mapToTaskDTO)
                .collect(Collectors.toList());
    }
}
