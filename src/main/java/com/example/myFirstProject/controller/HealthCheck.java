package com.example.myFirstProject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("/health")
    public String ValidateHealth(){
        return "Okay";
    }
    @GetMapping("/ping")
    public String validatePing(){
        return "Pong";
    }
}
