package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ZalozKonto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaloz_konto);

        EditText im = (EditText) findViewById(R.id.imie);
        String imie = im.getText().toString();

        EditText naz = (EditText) findViewById(R.id.nazwisko);
        String nazwisko = naz.getText().toString();

        EditText mail = (EditText) findViewById(R.id.email);
        String email = mail.getText().toString();

        EditText stud = (EditText) findViewById(R.id.kierunekStudiow);
        String kierunekStudiow = stud.getText().toString();

        EditText log = (EditText) findViewById(R.id.login2);
        String login = log.getText().toString();

        EditText passwd = (EditText) findViewById(R.id.haslo2);
        String haslo = passwd.getText().toString();

        Button zalozKontoButton2 = (Button) findViewById(R.id.zalozKontoButton2);
        zalozKontoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // wpis do bazy danych
                // sprawdzenie czy jest taki uzytkownik
                // dane tu?
                openStart();
            }
        });

        Button wsteczButton2 = (Button) findViewById(R.id.wsteczButton2);
        wsteczButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStart();
            }
        });

    }

    public void openStart() {
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
    }
}