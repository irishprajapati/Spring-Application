package com.example.myFirstProject;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.repository.JournalEntryRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
    public class DebugConfig {
    private final JournalEntryRepository journalEntryRepository;


        private final Environment env;

    public DebugConfig(JournalEntryRepository journalEntryRepository, Environment env) {
        this.journalEntryRepository = journalEntryRepository;
        this.env = env;
    }

    @PostConstruct
        public void printMongoConfig() {
        log.info("DB {}", env.getProperty("spring.data.mongodb.database"));
        log.info("URI {}", env.getProperty("spring.data.mongodb.uri"));
    }
    @PostConstruct
    public void testInsert(){
        JournalEntry entry = new JournalEntry();
        entry.setTitle("Atlas test");
        entry.setContent("This should go to Atlas");
        journalEntryRepository.save(entry);
        log.info("Inserted Test Document {}", entry);
    }
    }
