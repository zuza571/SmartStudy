package android.smartstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddNote extends AppCompatActivity {
    private EditText noteName;
    private TextView noteDate;
    private Button saveEdition;
    User currentUser;
    String login;
    List<String> currentUserData;
    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteName = findViewById(R.id.noteName);
        noteDate = findViewById(R.id.noteDate);
        saveEdition = findViewById(R.id.saveEditionButton);
        myDB = new DataBaseHelper(AddNote.this);
        currentUser = new User();
        currentUserData = new ArrayList<String>();

        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");

        // wpiasanie danych do currentUser
        currentUserData.addAll(myDB.current_user_data(login));
        currentUser.current_user(currentUser, currentUserData);

        noteDate.setText("Data: " + CalendarOperations.dateFormatter(CalendarOperations.selectedDate));

        saveEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNoteName = noteName.getText().toString();
                if (newNoteName.length() > 2) {
                    Note newNote = new Note(newNoteName, CalendarOperations.selectedDate, currentUser);
                    Note.notesList.add(newNote);
                    finish();
                } else {
                    Toast.makeText(AddNote.this, "Notatka jest za krótka!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cancelEdition (View view) {
        startActivity(new Intent(this, CalendarMain.class));
    }
}