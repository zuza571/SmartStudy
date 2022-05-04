package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // onclick listener do zalogujButton
        Button zalogujButton = (Button) findViewById(R.id.zalogujButton);
        zalogujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openZaloguj();
            }
        });

        Button zalozKontoButton = (Button) findViewById(R.id.zalozKontoButton);
        zalozKontoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openZalozKonto();
            }
        });
    }

    // otwarcie nowego okna
    public void openZaloguj() {
        Intent intent = new Intent(this, Zaloguj.class);
        startActivity(intent);
    }

    public void openZalozKonto() {
        Intent intent = new Intent(this, ZalozKonto.class);
        startActivity(intent);
    }
}