package com.example.myFirstProject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //specialized version of component + something else
public class myClass {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }
}
