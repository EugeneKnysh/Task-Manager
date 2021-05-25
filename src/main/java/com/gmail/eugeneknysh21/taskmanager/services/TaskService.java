package com.gmail.eugeneknysh21.taskmanager.services;

import com.gmail.eugeneknysh21.taskmanager.dto.TaskDTO;
import com.gmail.eugeneknysh21.taskmanager.repositories.projection.TasksCount;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Long addTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    void removeTask(Long id);

    List<TasksCount> getCountTasksByDayOnMonth(Integer year, Integer month);

    List<TaskDTO> getTasksForDay(LocalDate date);
}
