package android.smartstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class EditNote extends AppCompatActivity {
    private EditText noteName;
    private TextView noteDate, noteTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteName = findViewById(R.id.noteName);
        noteDate = findViewById(R.id.noteDate);

        noteDate.setText("Data: " + CalendarOperations.dateFormatter(CalendarOperations.selectedDate));
    }

    public void saveEdition(View view) {
        String newNoteName = noteName.getText().toString();
        Note newNote = new Note(newNoteName, CalendarOperations.selectedDate);
        Note.notesList.add(newNote);
        finish();
    }
}