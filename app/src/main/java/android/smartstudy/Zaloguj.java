package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Zaloguj extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaloguj);

        // zczytywanie loginu i hasla
        EditText log = (EditText) findViewById(R.id.login);
        String login = log.getText().toString();

        EditText passwd = (EditText) findViewById(R.id.haslo);
        String haslo = passwd.getText().toString();

        Button zalogujButton2 = (Button) findViewById(R.id.zalogujButton2);
        zalogujButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // haslo i login tu?
                // zapytanie bazy danych
                // sprobuj jeszcze raz
                openMainPage();
            }
        });

        Button wsteczButton = (Button) findViewById(R.id.wsteczButton);
        wsteczButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStart();
            }
        });
    }

    public void openMainPage() {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    public void openStart() {
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
    }
}