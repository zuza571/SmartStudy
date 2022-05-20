package android.smartstudy;

import static android.smartstudy.CalendarOperations.selectedDate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;

public class Timetable extends AppCompatActivity {

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

        CalendarOperations.selectedDate = LocalDate.now();
        setDayView();

        addLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void setDayView () {
        currentDay.setText(CalendarOperations.dayFormatter(selectedDate));
        previousDay.setText(CalendarOperations.dayFormatter(selectedDate.minusDays(1)));
        nextDay.setText(CalendarOperations.dayFormatter(selectedDate.plusDays(1)));
    }

    public void previousDay (View view) {
        CalendarOperations.selectedDate = CalendarOperations.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDay (View view) {
        CalendarOperations.selectedDate = CalendarOperations.selectedDate.plusDays(1);
        setDayView();
    }
}
