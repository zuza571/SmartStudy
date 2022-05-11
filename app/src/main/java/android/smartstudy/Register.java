package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    EditText name_input = (EditText) findViewById(R.id.name);
    EditText surname_input = (EditText) findViewById(R.id.surname);
    EditText email_input = (EditText) findViewById(R.id.email);
    EditText fieldOfStudies_input = (EditText) findViewById(R.id.fieldOfStudies);
    EditText login_input = (EditText) findViewById(R.id.login_register);
    EditText password_input = (EditText) findViewById(R.id.password_register);
    Button registerButton = (Button) findViewById(R.id.registerButton);
    Button backButton = (Button) findViewById(R.id.backButtonRegister);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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