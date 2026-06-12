package com.example.myFirstProject.controller;

import com.example.myFirstProject.entity.User;
import com.example.myFirstProject.repository.UserRepository;
import com.example.myFirstProject.service.UserService;
import org.bson.types.ObjectId;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
//    @GetMapping
//    public List<User> getAllUser(){
//        return userService.getAllUser();
//    }

    @GetMapping("/user/{myId}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id){
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping
    public boolean deleteUserByIdAndUserName(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        userService.deleteById(id);
        return true;
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            if(user.getPassword() != null && !user.getPassword().isBlank()){
                userInDb.setPassword(user.getPassword());
            }
            userService.saveUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
