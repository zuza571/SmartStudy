package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {
    String login;
    Button calendarButton, timetableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        calendarButton = findViewById(R.id.calendarButton);
        timetableButton = findViewById(R.id.timetableButton);
        TextView nextLesson = findViewById(R.id.nextLesson);
        TextView currentUser = findViewById(R.id.currentUserMainPage);

        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");

        nextLesson.setText("Następne zajęcia: " + "WPISAĆ");
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
}