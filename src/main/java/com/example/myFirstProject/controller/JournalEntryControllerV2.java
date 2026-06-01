package com.example.myFirstProject.controller;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @GetMapping
    public List<JournalEntry> getAll(){
        return null;

    }
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        System.out.println("Controller HIT");
        journalEntryService.saveEntry(myEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(myEntry);
    }
    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable String myId) {
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
