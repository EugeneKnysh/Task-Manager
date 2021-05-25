package com.gmail.eugeneknysh21.taskmanager.repositories;

import com.gmail.eugeneknysh21.taskmanager.entity.Task;
import com.gmail.eugeneknysh21.taskmanager.repositories.projection.TasksCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t.date, COUNT(t.id) as count FROM task t WHERE strftime('%Y-%m', t.date) = :year || '-' || :month GROUP BY t.date", nativeQuery=true)
    List<TasksCount> getCountTasksByDayOnMonth(@Param("year") String year, @Param("month") String month);

    List<Task> getAllByDateOrderByTimeAsc(String date);
}
