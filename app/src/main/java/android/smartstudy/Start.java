package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loginButton = (Button) findViewById(R.id.loginButtonStart);
        registerButton = (Button) findViewById(R.id.registerButtonStart);

        // onclick listener do loginButton
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }

    // otwarcie nowego okna
    public void openLoginActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}