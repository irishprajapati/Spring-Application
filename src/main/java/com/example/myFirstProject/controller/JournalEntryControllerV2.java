package com.example.myFirstProject.controller;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();

    }
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        System.out.println("Controller HIT");
        journalEntryService.saveEntry(myEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(myEntry);
    }
    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId).orElse(null);

    }
    @DeleteMapping("/id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId){
        journalEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("/id/{Id}")
    public JournalEntry updateJournalByEntry(@PathVariable ObjectId Id, @RequestBody JournalEntry newEntry){
    JournalEntry oldData = journalEntryService.findById(Id).orElse(null);
    if(oldData != null){
        oldData.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldData.getTitle());
        oldData.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getTitle(): oldData.getTitle());
    }
    journalEntryService.saveEntry(oldData);
    return oldData;
    }
}
