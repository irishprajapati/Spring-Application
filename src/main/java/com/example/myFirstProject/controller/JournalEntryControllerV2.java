package com.example.myFirstProject.controller;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.entity.User;
import com.example.myFirstProject.service.JournalEntryService;
import com.example.myFirstProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//combination of controller and response body
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    // helps to get the bean and make the class faster
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    //get method used as /journal/username -> simplified url
//    @GetMapping("/{userName}")
//    // response entity helps to get the response status code
//    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
//        //hold the userservice and the username that come into user variable
//        User userJournal =  userService.findByUserName(userName);
//        // make a list of all journal entry and put in into the variable user
//        List<JournalEntry> all= userJournal.getJournalEntries();
//        // check the condition of not null and not empty
//        if(all != null && !all.isEmpty()){
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
    // -> code for all journal entries
     // -> code to get individual user journal entries by username
    @GetMapping
    public ResponseEntity<?> getJournalEntriesByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userJournals = userService.findByUserName(userName);
        List<JournalEntry> journalData = userJournals.getJournalEntries();
        if(journalData != null && !journalData.isEmpty()){
            return new ResponseEntity<>(journalData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    // create the journal entry as the post method
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        /* try-catch method
        as the path variable is sent to check who is user
        ant he reqeust body check data and converts to json format
        */
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public  ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User byUserName = userService.findByUserName(userName);
        List<JournalEntry> collect =
                byUserName.getJournalEntries()
                        .stream()
                        .filter(x -> x.getId().equals(myId))
                        .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //-> debug mode of this code
    //-> url correct mapping should be journal/id/Erish/1233455
    @PutMapping("/id/{userName}/{myId}")
    // class name
    public ResponseEntity<?> updateJournalById(
            @PathVariable String userName,
            @PathVariable String myId,
            @RequestBody JournalEntry newEntry
    ) {
        ObjectId id = new ObjectId(myId);

        JournalEntry oldData = journalEntryService.findById(id).orElse(null);

        if (oldData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        oldData.setTitle(
                newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()
                        ? newEntry.getTitle()
                        : oldData.getTitle()
        );
        oldData.setContent(
                newEntry.getContent() != null && !newEntry.getContent().isEmpty()
                        ? newEntry.getContent()
                        : oldData.getContent()
        );
        journalEntryService.saveEntry(oldData);
        return new ResponseEntity<>(oldData, HttpStatus.OK);
    }
}
