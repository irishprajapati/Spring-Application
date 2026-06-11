package com.example.myFirstProject.service;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.entity.User;
import com.example.myFirstProject.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// -> service code are the core business logic written in spring which is called by controller
@Service
public class JournalEntryService {
JournalEntry journalEntry = new JournalEntry();
@Autowired
    private JournalEntryRepository journalEntryRepository;
//Autowiring means making a class from the bean which is already present in spring
    @Autowired
    private UserService userService;
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        // holding it to user and calling the userservice to find the user by username
        User user = userService.findByUserName(userName);
        //set the localtime of the journal now so that API doesnt have to sent the date
        journalEntry.setDate(LocalDateTime.now());
        // this line was just for the debug
        System.out.println("SERVICE HIT");
        // holding the saved journal entry so that user can be also added in that journal entry
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
        System.out.println("SAVED: " + saved);
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x-> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}