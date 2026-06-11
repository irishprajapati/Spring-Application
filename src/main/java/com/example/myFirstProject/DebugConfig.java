package com.example.myFirstProject;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.repository.JournalEntryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
    public class DebugConfig {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

        @Autowired
        private Environment env;

        @PostConstruct
        public void printMongoConfig() {
            System.out.println("DB: " + env.getProperty("spring.data.mongodb.database"));
            System.out.println("URI: " + env.getProperty("spring.data.mongodb.uri"));
        }
    @PostConstruct
    public void testInsert(){
        JournalEntry entry = new JournalEntry();
        entry.setTitle("Atlas test");
        entry.setContent("This should go to Atlas");
        journalEntryRepository.save(entry);
        System.out.println("Inserted Test Document");
    }
    }
