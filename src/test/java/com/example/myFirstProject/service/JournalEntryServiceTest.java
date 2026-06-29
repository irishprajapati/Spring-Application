package com.example.myFirstProject.service;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.entity.User;
import com.example.myFirstProject.repository.JournalEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JournalEntryServiceTest {
    @Mock
    private JournalEntryRepository journalEntryRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private JournalEntryService journalEntryService;

    @Test
    void shouldSaveEntryWhenUserExists() {
        //arrange
        String username = "database2";
        JournalEntry entry = new JournalEntry();
        User user = new User("database2", "erish");
        user.setJournalEntries(new ArrayList<>());
        when(userService.findByUserName(username)).thenReturn(user);
        when(journalEntryRepository.save(any())).thenReturn(entry);
        //act
        journalEntryService.saveEntry(entry, username);
        //assert
        verify(journalEntryRepository).save(entry);
        verify(userService).saveNewUser(user);
        assertNotNull(entry.getDate());
        assertNotNull(user.getJournalEntries().contains(entry));
    }

    @Test
    void shouldThrowWhenUserIsNull() {
        String username = "unknown";
        JournalEntry journalEntry = new JournalEntry();
        when(userService.findByUserName(username)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
            journalEntryService.saveEntry(journalEntry, username);
        });
        verify(journalEntryRepository, never()).save(any());
        verify(userService, never()).saveNewUser(any());
    }
    @Test
    void shouldThrowExceptionWhenEntryIsNull(){
        String username="database2";//entry is null not the username so provided the valid username

    }
}