package com.example.myFirstProject.repository;

import com.example.myFirstProject.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
//mongorepository -> to perform operations in mongodb
public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {
    
}
