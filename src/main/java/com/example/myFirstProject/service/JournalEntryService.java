package com.example.myFirstProject.service;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.entity.User;
import com.example.myFirstProject.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// -> service code are the core business logic written in spring which is called by controller
@Service
public class JournalEntryService {

//JournalEntry journalEntry = new JournalEntry();
private final JournalEntryRepository journalEntryRepository;
private final UserService userService;

    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }
    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {

        if (journalEntry == null) {
            throw new IllegalArgumentException("Journal entry cannot be null");
        }

        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty or null");
        }
        System.out.println("Service hit here..");
        System.out.println("JournalEntry: " + journalEntry);
        System.out.println("User before fetch: " + userName);
        User user = userService.findByUserName(userName);
        System.out.println("user fetched: " + user);
        System.out.println("User entries: " + user.getJournalEntries());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Ensure list is initialized (best practice: do this in entity)
        if (user.getJournalEntries() == null) {
            user.setJournalEntries(new ArrayList<>());
        }
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(savedEntry);
        userService.saveNewUser(user);
        return savedEntry;
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        if(userName == null && userName.isBlank()){
            throw new IllegalArgumentException("Username cannot be empty or null");
        }
        User user = userService.findByUserName(userName);
        if(user == null){
            throw new RuntimeException("User cannot be null");
        }
        boolean removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));
        if(!removed){
            throw new RuntimeException("Entry not found in user's list");
        }
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}
