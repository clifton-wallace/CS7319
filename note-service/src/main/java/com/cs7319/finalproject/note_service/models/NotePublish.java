package com.cs7319.finalproject.note_service.models;

// Model Is Used To Send Note Update To SNS
public class NotePublish {
    private Note note;
    private Action action;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
