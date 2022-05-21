package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    String login;
    Button calendarButton, timetableButton;
    private List<String> currentUserData;
    private User currentUserUser;
    private DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        calendarButton = findViewById(R.id.calendarButton);
        timetableButton = findViewById(R.id.timetableButton);
        Button logOutButton = findViewById(R.id.logOutButton);
        TextView nextLesson = findViewById(R.id.nextLesson);
        TextView currentUser = findViewById(R.id.currentUserMainPage);

        myDB = new DataBaseHelper(MainPage.this);
        currentUserUser = new User();
        currentUserData = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");
        currentUserData.addAll(myDB.current_user_data(login));
        currentUserUser.current_user(currentUserUser, currentUserData);

        String nextLessonString = nextLesson();
        nextLesson.setText(nextLessonString);
        currentUser.setText("Zalogowano jako: " + login);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar();
            }
        });

        timetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimetable();
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStart();
            }
        });
    }

    public void openTimetable () {
        Intent intent = new Intent(this, Timetable.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openCalendar() {
        Intent intent = new Intent(this, CalendarMain.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openStart() {
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
    }

    public String nextLesson() {
        String nextLesson = "";
        LocalTime now = LocalTime.now();
        int currentDay = LocalDate.now().getDayOfWeek().getValue();
        int timeDifference = 1000000;

        List<Lesson> lessons = myDB.getAllLessons(currentUserUser);

        for (int i = 0; i < lessons.size(); i++) {
            String lessonDayString = lessons.get(i).getDayOfWeek();
            int lessonDayInt = 0;
            String lessonDay = "";
            switch (lessonDayString) {
                case "Mon":
                    lessonDayInt = 1;
                    lessonDay = "Pon";
                    break;
                case "Tue":
                    lessonDayInt = 2;
                    lessonDay = "Wto";
                    break;
                case "Wed":
                    lessonDayInt = 3;
                    lessonDay = "Śro";
                    break;
                case "Thu":
                    lessonDayInt = 4;
                    lessonDay = "Czw";
                    break;
                case "Fri":
                    lessonDayInt = 5;
                    lessonDay = "Pt";
                    break;
                case "Sat":
                    lessonDayInt = 6;
                    lessonDay = "Sob";
                    break;
                case "Sun":
                    lessonDayInt = 7;
                    lessonDay = "Nie";
                    break;
                default:
                    break;
            }

            if (lessonDayInt > currentDay) {
                lessonDayInt = currentDay - lessonDayInt;
            } else if (currentDay == lessonDayInt){
                if (now.getHour() > lessons.get(i).getStartTime().getHour()) {
                    lessonDayInt = 7;
                } else if (now.getHour() < lessons.get(i).getStartTime().getHour()) {
                    lessonDayInt = 0;
                    lessonDay = "dziś";
                }
            } else if (currentDay == lessonDayInt && now.getHour() == lessons.get(i).getStartTime().getHour()) {
                if (lessons.get(i).getStartTime().getMinute() > now.getMinute()) {
                    lessonDayInt = 7;
                    lessonDay = "dziś";
                } else {
                    lessonDayInt = 0;
                }
            }
            else {
                lessonDayInt = 7 - Math.abs(currentDay - lessonDayInt);
            }
            int currentTimeDifference = (lessons.get(i).getStartTime().getHour() - now.getHour()) * 60 + (lessons.get(i).getStartTime().getMinute() - now.getMinute()) + (lessonDayInt * 24 * 60);

            if (currentTimeDifference < timeDifference && currentTimeDifference > 0) {
                timeDifference = currentTimeDifference;
                nextLesson = lessons.get(i).getText() + " (" + lessonDay + " " + lessons.get(i).getStartTime() + ") " +
                        lessons.get(i).getRoom();
            } else {
                currentTimeDifference = Math.abs(currentTimeDifference);
                if (currentTimeDifference < timeDifference) {
                    timeDifference = currentTimeDifference;
                    nextLesson = lessons.get(i).getText() + " (" + lessonDay + " " + lessons.get(i).getStartTime() + ") " +
                            lessons.get(i).getRoom();
                }
            }
        }

        String next = "";

        if (!nextLesson.equals(""))
            next = "Następne zajęcia: " + nextLesson;

        return next;
    }
}