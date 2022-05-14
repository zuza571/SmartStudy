package android.smartstudy;

import java.time.LocalDate;
import java.util.ArrayList;

public class Note {
    public static ArrayList<Note> notesList = new ArrayList<>();
    private String name;
    private LocalDate date;
    private User ownerNote;
    public static Note selectedNote;

    // przypisanie do daty
    public static ArrayList<Note> notesForDate(LocalDate date) {
        ArrayList<Note> notes = new ArrayList<>();
        for (Note note : notesList) {
            if(note.getDate().equals(date))
                notes.add(note);
        }
        return notes;
    }

    public Note(String name, LocalDate date, User ownerNote) {
        this.name = name;
        this.date = date;
        this.ownerNote = ownerNote;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getOwnerNote() {
        return ownerNote;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setOwnerNote(User ownerNote) {
        this.ownerNote = ownerNote;
    }
}
