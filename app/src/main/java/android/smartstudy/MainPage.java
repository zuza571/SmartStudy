package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    String login;
    Button calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        calendarButton = findViewById(R.id.calendarButton);

        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar();
            }
        });

    }

    public void timetable (View view) {
        startActivity(new Intent(this, Timetable.class));
    }

    public void openCalendar() {
        Intent intent = new Intent(this, CalendarMain.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}