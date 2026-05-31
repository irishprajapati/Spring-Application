package com.example.myFirstProject.controller;

import com.example.myFirstProject.entity.JournalEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class JournalEntryControllerV2 {
    @GetMapping
    public List<JournalEntry> getAll(){
        return null;

    }
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        return null;
    }
    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable long myId) {
        return null;

    }
    @DeleteMapping("/id/{myId}")
    public JournalEntry deleteEntryById(@PathVariable long myId){
    return null;
    }

    @PutMapping("/id/{Id}")
    public JournalEntry updateJournalByEntry(@PathVariable long Id, @RequestBody JournalEntry myEntry){
    return null;
    }
}
