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

        Button kalendzarzButton = (Button) findViewById(R.id.kalendarzButton);
        kalendzarzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKalendzarz();
            }
        });
    }
    public void openKalendzarz() {
        Intent intent = new Intent(this, Kalendarz.class);
        startActivity(intent);
    }
}