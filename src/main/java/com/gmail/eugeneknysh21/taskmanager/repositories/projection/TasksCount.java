package com.gmail.eugeneknysh21.taskmanager.repositories.projection;

import java.time.LocalDate;

public interface TasksCount {
    LocalDate getDate();

    Long getCount();
}
