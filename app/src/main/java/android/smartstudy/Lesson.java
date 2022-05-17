package android.smartstudy;

import java.time.LocalDate;
import java.time.LocalTime;

public class Lesson {
    private int id;
    private LocalTime startTime;
    private LocalDate dayOfWeek;
    private String text;
    private int duration;
    private User lessonOwner;

    public Lesson(int id, LocalTime startTime, LocalDate dayOfWeek, String text, int duration, User lessonOwner) {
        this.id = id;
        this.startTime = startTime;
        this.dayOfWeek = dayOfWeek;
        this.text = text;
        this.duration = duration;
        this.lessonOwner = lessonOwner;
    }

    public int getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getDayOfWeek() {
        return dayOfWeek;
    }

    public String getText() {
        return text;
    }

    public int getDuration() {
        return duration;
    }

    public User getLessonOwner() {
        return lessonOwner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setDayOfWeek(LocalDate dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLessonOwner(User lessonOwner) {
        this.lessonOwner = lessonOwner;
    }
}
