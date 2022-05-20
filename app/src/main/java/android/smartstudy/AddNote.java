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
    private User currentUser;
    private String login;
    private List<String> currentUserData;
    private DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText noteName = findViewById(R.id.noteName);
        TextView noteDate = findViewById(R.id.noteDate);
        Button saveEdition = findViewById(R.id.saveEditionButton);
        Button cancelEdition = findViewById(R.id.cancelEditionButton);
        myDB = new DataBaseHelper(AddNote.this);
        currentUser = new User();
        currentUserData = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");

        // wpiasanie danych do currentUser
        currentUserData.addAll(myDB.current_user_data(login));
        currentUser.current_user(currentUser, currentUserData);

        noteDate.setText("Data: " + CalendarOperations.dateFormatter(CalendarOperations.selectedDate));

        cancelEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCancelEdition();
            }
        });

        saveEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNoteName = noteName.getText().toString();
                if (newNoteName.length() > 2) {
                    Note newNote = new Note(newNoteName, CalendarOperations.selectedDate, currentUser);
                    // Note.notesList.add(newNote);
                    myDB.addNote(newNote);
                    activitySaveEdition();
                } else {
                    Toast.makeText(AddNote.this, "Notatka jest za krótka!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void activityCancelEdition () {
        Intent intent = new Intent(this, CalendarMain.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void activitySaveEdition () {
        Intent intent = new Intent(this, CalendarMain.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}