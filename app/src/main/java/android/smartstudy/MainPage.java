package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    public void timetable (View view) {
        startActivity(new Intent(this, Timetable.class));
    }

    public void calendar (View view) {
        startActivity(new Intent(this, CalendarMain.class));
    }
}