package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddLesson extends AppCompatActivity {
    private User currentUser;
    private String login;
    private List<String> currentUserData;
    private DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        Button cancel = findViewById(R.id.cancelLesson);
        Button save = findViewById(R.id.saveLesson);
        TextView dayOfWeek = findViewById(R.id.dayOfWeek);
        EditText lessonName = findViewById(R.id.lessonName);
        EditText hours = findViewById(R.id.hours);
        EditText minutes = findViewById(R.id.minutes);
        EditText duration = findViewById(R.id.duration);
        EditText room = findViewById(R.id.room);
        myDB = new DataBaseHelper(AddLesson.this);
        currentUser = new User();
        currentUserData = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");

        currentUserData.addAll(myDB.current_user_data(login));
        currentUser.current_user(currentUser, currentUserData);

        dayOfWeek.setText(CalendarOperations.selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelSaving();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newLessonName = lessonName.getText().toString();
                int hour = Integer.parseInt(hours.getText().toString());
                int mins = Integer.parseInt(minutes.getText().toString());
                String hoursAndMinutes;
                if (hour < 10) {
                   hoursAndMinutes = "0" + hour + ":" + mins;
                }
                else if (mins == 0) {
                    hoursAndMinutes = hour + ":" + mins + "0";
                } else {
                    hoursAndMinutes = hour + ":" + mins;
                }

                int newLessonDuration = Integer.parseInt(duration.getText().toString());
                String newLessonRoom = room.getText().toString();

                if (newLessonName.length() > 1 && hour >= 0 && hour <= 23 && mins >= 0 && mins <= 59
                && newLessonRoom.length() > 0 && newLessonDuration >= 20 && newLessonDuration <= 200) {
                    LocalTime startTime = LocalTime.parse(hoursAndMinutes, DateTimeFormatter.ISO_LOCAL_TIME);
                    Lesson newLesson = new Lesson(newLessonName, startTime, CalendarOperations.selectedDate,
                            newLessonRoom, newLessonDuration, currentUser);
                    myDB.addLesson(newLesson);
                    saveLesson();
                } else {
                    Toast.makeText(AddLesson.this, "Wprowadzone dane są niepoprawne lub za krótkie",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    public void cancelSaving() {
        Intent intent = new Intent(this, Timetable.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void saveLesson() {
        Intent intent = new Intent(this, Timetable.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}