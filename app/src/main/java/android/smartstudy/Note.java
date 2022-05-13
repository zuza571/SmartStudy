package android.smartstudy;

import java.time.LocalDate;
import java.util.ArrayList;

public class Note {
    public static ArrayList<Note> notesList = new ArrayList<>();
    private String name;
    private LocalDate date;
    public static Note selectedNote;
    public static ArrayList<Note> notes = new ArrayList<>();

    // przypisanie do daty
    public static ArrayList<Note> notesForDate(LocalDate date) {
        for (Note note : notesList) {
            if(note.getDate().equals(date))
                notes.add(note);
        }
        return notes;
    }

    public Note(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
