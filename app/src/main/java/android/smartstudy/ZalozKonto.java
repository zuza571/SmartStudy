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

        EditText imie_input = (EditText) findViewById(R.id.imie);
        EditText nazwisko_imput = (EditText) findViewById(R.id.nazwisko);
        EditText email_input = (EditText) findViewById(R.id.email);
        EditText studia_input = (EditText) findViewById(R.id.kierunekStudiow);
        EditText login_input = (EditText) findViewById(R.id.login2);
        EditText haslo_input = (EditText) findViewById(R.id.haslo2);

        Button zalozKontoButton2 = (Button) findViewById(R.id.zalozKontoButton2);
        zalozKontoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // wpis do bazy danych
                BazaDanychHelper mojaDB = new BazaDanychHelper(ZalozKonto.this);
                mojaDB.dodajUzytkownika(imie_input.getText().toString(),
                        nazwisko_imput.getText().toString(),
                        email_input.getText().toString(),
                        studia_input.getText().toString(),
                        login_input.getText().toString(),
                        haslo_input.getText().toString());
                // sprawdzenie czy jest taki uzytkownik

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