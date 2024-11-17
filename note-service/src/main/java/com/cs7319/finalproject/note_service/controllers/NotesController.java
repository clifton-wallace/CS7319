package com.cs7319.finalproject.note_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs7319.finalproject.note_service.models.ApiResponse;
import com.cs7319.finalproject.note_service.models.Note;
import com.cs7319.finalproject.note_service.models.NoteDto;
import com.cs7319.finalproject.note_service.services.NoteService;

// Controller for accessing notes functions such as create, update, delete and get
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NoteService noteService;

    // Method Add/Registers A New Note
    @PostMapping
    public ResponseEntity<ApiResponse<Note>> createNote(@RequestBody NoteDto note) {
         try {

            Note createdNote = noteService.createNote(note);

            return ResponseEntity.ok(
                new ApiResponse<>(true, "Note Created Successfully", createdNote));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }

    // Method Updates An Existing Note
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Note>> updateNote(@PathVariable String id, @RequestBody NoteDto updatedNote) {
        try {
            Note updated =  noteService.updateNote(id, updatedNote);
             //publishService.publishMessage("Note created: " + createdNote.toString());
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Note Updated Successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }

     // Method Deletes A Note
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNote(@PathVariable String id) {
        try {
            noteService.deleteNote(id);
             //publishService.publishMessage("Note created: " + createdNote.toString());
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Note Deleted Successfully", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }

     // Method Gets A Note Based On Its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Note>> getNoteById(@PathVariable String id) {
        try {
            Note note = noteService.getNoteById(id);
            if (note != null) {
                return ResponseEntity.ok(
                    new ApiResponse<>(true, "Note Successfully Found", note));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(false, "No Note Found For ID: " + id, null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }

    // Method Gets All Notes
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Note>>> getNotes() {
        try {
            List<Note> notes = noteService.getNotes();
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Notes Successfully Found", notes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "An unexpected error occurred. Error: " + e.getMessage(), null));
        }
    }
}