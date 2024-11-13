package com.cs7319.finalproject.note_service.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.cs7319.finalproject.note_service.models.Note;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

// Repository For Interacting With NoSQL Database That Stores Note Data
@Repository
public class NoteRepository {

    private final DynamoDbTable<Note> noteTable;

    public NoteRepository(DynamoDbEnhancedClient enhancedClient) {
        this.noteTable = enhancedClient.table("Notes", TableSchema.fromBean(Note.class));
    }

    // Create New Note
    public Note create(Note note) {
        noteTable.putItem(note);
        return note;
    }

    // Update Existing Note
    public Note update(Note note) {
        noteTable.updateItem(note);
        return note;
    }

    // Delete Item Based On ID
    public void deleteById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        noteTable.deleteItem(key);
    }

    // Find Record Bassed On ID
    public Note findById(String id) {
        Key getKey = Key.builder().partitionValue(id).build();
        return noteTable.getItem(getKey);
    }

    // Return All Note Reocrds
    public List<Note> findAll() {
        return noteTable.scan().items().stream().collect(Collectors.toList());
    }
}