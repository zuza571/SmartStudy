package android.smartstudy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Note {
    public static ArrayList<Note> notesList = new ArrayList<>();
    private int id;
    private String name;
    private LocalDate date;
    private User noteOwner;

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

    @Override
    public String toString() {
        return name;
    }
}
