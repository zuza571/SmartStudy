package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

        dayOfWeek.setText(CalendarOperations.dayFormatter(CalendarOperations.selectedDate));

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

                String hoursAndMinutes = hours.getText() + ":" + minutes.getText();

                LocalTime startTime = LocalTime.parse(hoursAndMinutes, DateTimeFormatter.ISO_LOCAL_TIME);
                System.out.println(startTime);

                /*
                int newLessonDuration = duration.getText();
                String newLessonRoom = room.getText().toString();

                Lesson newLesson = new Lesson(newLessonName, startTime, CalendarOperations.selectedDate,
                        newLessonRoom, newLessonDuration, currentUser);
                myDB.addLesson(newLesson);

                 */
                saveLesson();
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