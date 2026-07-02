package com.example.myFirstProject.controller;

import com.example.myFirstProject.entity.User;
import com.example.myFirstProject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health")
    public String validateHealth() {
       log.info("logs working strategy in CLI");
        // this is to check whether the API endpoints are working fine or not in browser
        return "Okay";

    }

    @GetMapping("/ping")
    public String validatePing() {
        return "Pong";

    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        } catch (Exception e) {
            // why this line is being executed
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
