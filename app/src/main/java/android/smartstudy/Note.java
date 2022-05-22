package android.smartstudy;

import java.time.LocalDate;

public class Note {
    private String text;
    private final LocalDate date;
    private final User noteOwner;
    public static String selectedNote = "";

    public Note (String text, LocalDate date, User noteOwner) {
        this.text = text;
        this.date = date;
        this.noteOwner = noteOwner;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getNoteOwner() {
        return noteOwner;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
