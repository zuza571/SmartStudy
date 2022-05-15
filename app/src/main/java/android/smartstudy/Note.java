package android.smartstudy;

import java.time.LocalDate;
import java.util.ArrayList;

public class Note {
    public static ArrayList<Note> notesList = new ArrayList<>();
    private String name;
    private LocalDate date;
    private User noteOwner;
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

    public Note(String name, LocalDate date, User noteOwner) {
        this.name = name;
        this.date = date;
        this.noteOwner = noteOwner;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getNoteOwner() {
        return noteOwner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNoteOwner(User noteOwner) {
        this.noteOwner = noteOwner;
    }
}
