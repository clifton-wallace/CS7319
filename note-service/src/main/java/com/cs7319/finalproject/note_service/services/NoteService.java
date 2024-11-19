package com.cs7319.finalproject.note_service.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cs7319.finalproject.note_service.models.Action;
import com.cs7319.finalproject.note_service.models.Note;
import com.cs7319.finalproject.note_service.models.NoteDto;
import com.cs7319.finalproject.note_service.repositories.NoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

// Service Provides Note Functionality Such As: Create, Update, Delete, And Find
// Includes only the necessary functions for this project, representing a subset of a real application.
@Service
public class NoteService {

    @Value("${publish.enabled}")
    private boolean publishEnabled;

    @Autowired
    private PublishService publishService;

    @Autowired
    private NoteRepository noteRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Create New Note
    public Note createNote(NoteDto note) throws JsonProcessingException {
        Note newNote = new Note();
        newNote.setId(UUID.randomUUID().toString());
        newNote.setTitle(note.getTitle());
        newNote.setContent(note.getContent());
        newNote.setCreatedByUserId(note.getUserId());
        newNote.setLastUpdatedByUserId(note.getUserId());
        newNote.setCreated(LocalDateTime.now().format(formatter));
        newNote.setlastUpdated(LocalDateTime.now().format(formatter));
        newNote = noteRepository.create(newNote);

        if (publishEnabled) { 
            publishService.publishMessage(Action.CREATE, newNote);
        }

        return newNote;
    }

    // Update Existing Note
    public Note updateNote(String id, NoteDto updatedNote) throws JsonProcessingException {
        Note existingNote = noteRepository.findById(id);
        
        if (existingNote == null) {
            throw new IllegalArgumentException("Note not found with ID: " + id);
        }
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        existingNote.setLastUpdatedByUserId(updatedNote.getUserId());
        existingNote.setlastUpdated(LocalDateTime.now().format(formatter));
        existingNote =  noteRepository.update(existingNote);
        
        if (publishEnabled) { 
            publishService.publishMessage(Action.UPDATE, existingNote);
        }

        return existingNote;
    }

    // Delete Note
    public void deleteNote(String id) throws JsonProcessingException {
        Note existingNote = noteRepository.findById(id);
        
        if (existingNote == null) {
            throw new IllegalArgumentException("Note not found with ID: " + id);
        }

        noteRepository.deleteById(id);

        if (publishEnabled) { 
            publishService.publishMessage(Action.DELETE, existingNote);
        }
    }

    // Get Note By ID
    public Note getNoteById(String id) {
        return noteRepository.findById(id);
    }

    // Get All Notes
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }
}