package com.gmail.eugeneknysh21.taskmanager.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String text;

    public Task() {
    }

    public Task(LocalDate date, LocalTime time, String text) {
        this.date = date.toString();
        this.time = time.toString();
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public void setDate(LocalDate date) {
        this.date = date.toString();
    }

    public LocalTime getTime() {
        return LocalTime.parse(time);
    }

    public void setTime(LocalTime time) {
        this.time = time.toString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
