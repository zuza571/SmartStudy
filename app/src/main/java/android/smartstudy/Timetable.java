package android.smartstudy;

import static android.smartstudy.CalendarOperations.selectedDate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Timetable extends AppCompatActivity {
    private User currentUser;
    private String login;
    private List<String> currentUserData;
    private DataBaseHelper myDB;
    private TextView currentDay, previousDay, nextDay;
    private Button addLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        currentDay = findViewById(R.id.currentDay);
        previousDay = findViewById(R.id.previousDayButton);
        nextDay = findViewById(R.id.nextDayButton);
        addLesson = findViewById(R.id.addLessonButton);
        ListView lvHours = findViewById(R.id.hoursListView);

        myDB = new DataBaseHelper(Timetable.this);
        currentUser = new User();
        currentUserData = new ArrayList<>();

        // wczytanie currentUser
        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");
        currentUserData.addAll(myDB.current_user_data(login));
        currentUser.current_user(currentUser, currentUserData);

        CalendarOperations.selectedDate = LocalDate.now();
        setDayView();

        addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddLesson();
            }
        });


    }

    private void setDayView () {
        currentDay.setText(selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        previousDay.setText(selectedDate.minusDays(1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        nextDay.setText(selectedDate.plusDays(1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        fillLessonLv();
    }

    public void previousDay (View view) {
        CalendarOperations.selectedDate = CalendarOperations.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDay (View view) {
        CalendarOperations.selectedDate = CalendarOperations.selectedDate.plusDays(1);
        setDayView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayView();
    }

    private void fillLessonLv () {
        //ArrayList
    }



    public void openAddLesson() {
        Intent intent = new Intent(this, AddLesson.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
