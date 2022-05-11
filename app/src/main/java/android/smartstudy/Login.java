package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
   EditText login_input, password_input;
   Button loginButton, backButton;
   DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_input = findViewById(R.id.login);
        password_input = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);
        myDB = new DataBaseHelper(Login.this);

        // zczytywanie loginu i hasla
        String login = login_input.getText().toString();
        String password = password_input.getText().toString();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // logowanie przez sprawdzenie bazy danych
                if (myDB.login_user(login_input.getText().toString(), password_input.getText().toString())) {
                    openMainPage();
                } else {
                    Toast.makeText(Login.this, "Spróbuj jeszcze raz!", Toast.LENGTH_SHORT).show();
                }
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