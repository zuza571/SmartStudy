package android.smartstudy;

import java.time.LocalTime;

public class Lesson {
    private final LocalTime startTime;
    private final String dayOfWeek;
    private final String room;
    private String text;
    private final int duration;
    private final User lessonOwner;
    static String selectedLesson;

    public Lesson(LocalTime startTime, String dayOfWeek, String room, String text, int duration, User lessonOwner) {
        this.startTime = startTime;
        this.dayOfWeek = dayOfWeek;
        this.room = room;
        this.text = text;
        this.duration = duration;
        this.lessonOwner = lessonOwner;
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

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
