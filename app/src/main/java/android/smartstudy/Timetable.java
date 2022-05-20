package android.smartstudy;

import static android.smartstudy.CalendarOperations.selectedDate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    ListView lvLessons;
    static List <Lesson> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        currentDay = findViewById(R.id.currentDay);
        previousDay = findViewById(R.id.previousDayButton);
        nextDay = findViewById(R.id.nextDayButton);
        Button addLesson = findViewById(R.id.addLessonButton);
        Button deleteLesson = findViewById(R.id.deleteLessonButton);
        Button backButton = findViewById(R.id.timetableToMainPage);
        lvLessons = findViewById(R.id.lessonsListView);

        myDB = new DataBaseHelper(Timetable.this);
        currentUser = new User();
        currentUserData = new ArrayList<>();

        // wczytanie currentUser
        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");
        currentUserData.addAll(myDB.current_user_data(login));
        currentUser.current_user(currentUser, currentUserData);

        lessons = myDB.getAllLessons(currentUser);

        CalendarOperations.selectedDate = LocalDate.now();

        setDayView();

        lvLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Lesson.selectedLesson = String.valueOf(adapterView.getItemAtPosition(position));
                setDayView();
            }
        });

        addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddLesson();
            }
        });

        deleteLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lessons = myDB.deleteLesson(Lesson.selectedLesson, lessons, currentUser);
                setDayView();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();
            }
        });
    }

    private void setDayView () {
        currentDay.setText(selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        previousDay.setText(selectedDate.minusDays(1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        nextDay.setText(selectedDate.plusDays(1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        fillLessonListView();
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
        fillLessonListView();
    }

    private void fillLessonListView() {
        List <Lesson> dailyLessons = new ArrayList<>();

        for (int i = 0; i < lessons.size(); i++) {
            if (CalendarOperations.dayFormatter(selectedDate).equals(lessons.get(i).getDayOfWeek())) {
                dailyLessons.add(lessons.get(i));
                System.out.println(lessons.get(i).getText());
            }
        }

        String [] lessonsToString = new String[dailyLessons.size()];

        int i = 0;
        for (Lesson lesson : dailyLessons) {
            lessonsToString[i] = lesson.getText();
            i += 1;
        }

        // adapter
        TimetableAdapter adapter = new TimetableAdapter(this, lessonsToString);
        lvLessons.setAdapter(adapter);
    }
    
    public void openAddLesson() {
        Intent intent = new Intent(this, AddLesson.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openMainPage() {
        Intent intent = new Intent(this, MainPage.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
