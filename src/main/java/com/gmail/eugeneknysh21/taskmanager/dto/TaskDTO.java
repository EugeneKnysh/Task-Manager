package com.gmail.eugeneknysh21.taskmanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public class TaskDTO {

    private Long id;

    @NotNull(message = "Date can`t be empty.")
    private LocalDate date;

    @NotNull(message = "Time can`t be empty.")
    private LocalTime time;

    @NotBlank(message = "Text can`t be empty.")
    @Size(min = 1, max = 255, message = "Text can`t be more than 255 characters.")
    private String text;

    public TaskDTO() {
    }

    public TaskDTO(Long id, LocalDate date, LocalTime time, String text) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
