package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText log = (EditText) findViewById(R.id.login);
    EditText passwd = (EditText) findViewById(R.id.password);
    Button loginButton = (Button) findViewById(R.id.loginButton);
    Button backButton = (Button) findViewById(R.id.backButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // zczytywanie loginu i hasla
        String login = log.getText().toString();
        String password = passwd.getText().toString();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // password i login tu?
                // zapytanie bazy danych
                // sprobuj jeszcze raz
                openMainPage();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
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