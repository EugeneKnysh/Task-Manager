package com.gmail.eugeneknysh21.taskmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String indexPage() {
        return "index";
    }
}
