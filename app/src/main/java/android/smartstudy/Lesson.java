package android.smartstudy;

import java.time.LocalTime;

public class Lesson {
    private int id;
    private LocalTime startTime;
    private String dayOfWeek;
    private String room;
    private String text;
    private int duration;
    private User lessonOwner;
    static String selectedLesson;

    public Lesson(LocalTime startTime, String dayOfWeek, String room, String text, int duration, User lessonOwner) {
        this.startTime = startTime;
        this.dayOfWeek = dayOfWeek;
        this.room = room;
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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getRoom() {
        return room;
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

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setRoom(String room) {
        this.room = room;
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

    @Override
    public String toString() {
        return text;
    }
}
