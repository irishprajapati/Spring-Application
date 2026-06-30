package com.example.myFirstProject.service;

import com.example.myFirstProject.entity.JournalEntry;
import com.example.myFirstProject.entity.User;
import com.example.myFirstProject.repository.JournalEntryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
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
    @Nested
    class SaveEntryTests {
        @Test
        void shouldSaveEntryWhenUserExists() {
            String username = "database2";
            JournalEntry entry = new JournalEntry();
            User user = new User(username, "erish");
            user.setJournalEntries(new ArrayList<>());
            when(userService.findByUserName(username)).thenReturn(user);
            when(journalEntryRepository.save(any())).thenReturn(entry);
            journalEntryService.saveEntry(entry, username);
            verify(journalEntryRepository).save(entry);
            verify(userService).saveNewUser(user);
            assertNotNull(entry.getDate());
            assertTrue(user.getJournalEntries().contains(entry));
        }

        @Test
        void shouldThrowWhenUserIsNull() {
            String username = "unknown";
            JournalEntry entry = new JournalEntry();
            when(userService.findByUserName(username)).thenReturn(null);
            assertThrows(RuntimeException.class, () -> {
                journalEntryService.saveEntry(entry, username);
            });
            verify(journalEntryRepository, never()).save(any());
            verify(userService, never()).saveNewUser(any());
        }

        @Test
        void shouldThrowWhenUsernameIsBlank() {
            String username = "";
            JournalEntry entry = new JournalEntry();
            assertThrows(IllegalArgumentException.class, () -> {
                journalEntryService.saveEntry(entry, username);
            });
            verifyNoInteractions(userService);
            verifyNoInteractions(journalEntryRepository);
        }

        @Test
        void shouldInitializeJournalListIfNull() {
            String username = "nehaa";
            JournalEntry entry = new JournalEntry();
            User user = new User(username, "nehaa123");
            user.setJournalEntries(null);
            when(userService.findByUserName(username)).thenReturn(user);
            when(journalEntryRepository.save(any())).thenReturn(entry);
            journalEntryService.saveEntry(entry, username);
            assertNotNull(user.getJournalEntries()); // state fixed
            assertTrue(user.getJournalEntries().contains(entry));
        }
    }
    @Nested
    class DeleteEntryTests
}