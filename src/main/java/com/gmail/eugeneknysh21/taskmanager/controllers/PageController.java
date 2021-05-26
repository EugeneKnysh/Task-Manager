package com.gmail.eugeneknysh21.taskmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    @GetMapping("/")
    public String getPage(@RequestParam(required = false) Integer y,
                            @RequestParam(required = false) Integer m,
                            @RequestParam(required = false) Integer d) {
        if (y != null && m != null && d != null) {
            return "day";
        }
        return "index";
    }
}
