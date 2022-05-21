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
        nextLesson.setText("Następne zajęcia: " + nextLessonString);
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

    public String nextLesson() {
        String nextLesson = "";
        LocalTime now = LocalTime.now();
        int currentDay = LocalDate.now().getDayOfWeek().getValue();
        int timeDifference = 1000000;

        List<Lesson> lessons = myDB.getAllLessons(currentUserUser);

        for (int i = 0; i < lessons.size(); i++) {
            String noteDayString = lessons.get(i).getDayOfWeek();
            int noteDayInt = 0;
            switch (noteDayString) {
                case "Mon":
                    noteDayInt = 1;
                    break;
                case "Tue":
                    noteDayInt = 2;
                    break;
                case "Wed":
                    noteDayInt = 3;
                    break;
                case "Thu":
                    noteDayInt = 4;
                    break;
                case "Fri":
                    noteDayInt = 5;
                    break;
                case "Sat":
                    noteDayInt = 6;
                    break;
                case "Sut":
                    noteDayInt = 7;
                    break;
                default:
                    break;
            }

            if (noteDayInt > currentDay) {
                noteDayInt = currentDay - noteDayInt;
            } else if (currentDay == noteDayInt && now.getHour() < lessons.get(i).getStartTime().getHour()){
                noteDayInt = 7;
            } else if (currentDay == noteDayInt && now.getHour() == lessons.get(i).getStartTime().getHour()) {
                if (lessons.get(i).getStartTime().getMinute() > now.getMinute()) {
                    noteDayInt = 7;
                } else {
                    noteDayInt = 0;
                }
            }
            else {
                noteDayInt = 7 - Math.abs(currentDay - noteDayInt);
            }
            int helper = 0;
            int currentTimeDifference = (lessons.get(i).getStartTime().getHour() - now.getHour()) * 60 + (lessons.get(i).getStartTime().getMinute() - now.getMinute()) + (noteDayInt * 24 * 60);
            if (currentTimeDifference < timeDifference && currentTimeDifference > 0) {
                timeDifference = currentTimeDifference;
                nextLesson = lessons.get(i).getText();
                helper = 1;
            } else if (helper == 0){
                currentTimeDifference = Math.abs(currentTimeDifference);
                if (currentTimeDifference < timeDifference) {
                    timeDifference = currentTimeDifference;
                    nextLesson = lessons.get(i).getText();
                }
            }
        }

        return nextLesson;
    }
}