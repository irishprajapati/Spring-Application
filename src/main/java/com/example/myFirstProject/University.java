package com.example.myFirstProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class University {
    @Autowired // -> Dependency injection
    private Student student;

    @GetMapping("/Information")
    public String information(){
        return student.Data();
    }
}
