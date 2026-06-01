package com.example.myFirstProject.service;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalEntryService {
@Autowired
    private JournalEntryRepository journalEntryRepository;
    public JournalEntry saveEntry(JournalEntry journalEntry){
        System.out.println("SERVICE HIT");

        JournalEntry saved = journalEntryRepository.save(journalEntry);

        System.out.println("SAVED: " + saved);

        return saved;
    }
}