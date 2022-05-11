package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    EditText name_input, surname_input, email_input, fieldOfStudies_input,
    login_input, password_input;
    Button registerButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_input = (EditText) findViewById(R.id.name);
        surname_input = (EditText) findViewById(R.id.surname);
        email_input = (EditText) findViewById(R.id.email);
        fieldOfStudies_input = (EditText) findViewById(R.id.fieldOfStudies);
        login_input = (EditText) findViewById(R.id.login_register);
        password_input = (EditText) findViewById(R.id.password_register);
        registerButton = (Button) findViewById(R.id.registerButton);
        backButton = (Button) findViewById(R.id.backButtonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // wpis do bazy danych
                DataBaseHelper myDB = new DataBaseHelper(Register.this);
                myDB.addUser(name_input.getText().toString(),
                        surname_input.getText().toString(),
                        email_input.getText().toString(),
                        fieldOfStudies_input.getText().toString(),
                        login_input.getText().toString(),
                        password_input.getText().toString());
                // sprawdzenie czy jest taki uzytkownik

                openStart();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStart();
            }
        });

    }

    public void openStart() {
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
    }
}