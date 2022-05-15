package android.smartstudy;

import static android.smartstudy.CalendarOperations.monthYearFormatter;
import static android.smartstudy.CalendarOperations.daysOfMonthMethod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarMain extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView currentMonth, previousMonth, nextMonth;
    private RecyclerView recyclerView; // okno z wszystkimi dniami
    private ListView lvNotesList;
    private Button goToCurrentMonth, deleteNote, addNote;
    private String login;
    private List<String> currentUserData;
    private User currentUser;
    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        recyclerView = findViewById(R.id.recyclerView);
        currentMonth = findViewById(R.id.currentMonth);
        previousMonth = findViewById(R.id.previousMonthButton);
        nextMonth = findViewById(R.id.nextMonthButton);
        goToCurrentMonth = findViewById(R.id.goToCurrentMonth);
        deleteNote = findViewById(R.id.deleteNote);
        addNote = findViewById(R.id.addNoteButton);
        lvNotesList = findViewById(R.id.lvNotesList);

        myDB = new DataBaseHelper(CalendarMain.this);
        currentUser = new User();
        currentUserData = new ArrayList<>();

        // wczytanie currentUser
        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");
        currentUserData.addAll(myDB.current_user_data(login));
        currentUser.current_user(currentUser, currentUserData);

        CalendarOperations.selectedDate = LocalDate.now();
        setMonthView();

        //------------------------------------------------------------------------------------------
        // nie dziala


        // zmiana notatek na String
        if(!Note.notesList.isEmpty()) {
            String[] notesToString = fillListView();
        }
        /*
        List<String> notesListToString = new ArrayList<>();
        for(int i = 0; i < Note.notesList.size(); i++) {
            notesListToString.add(Note.notesList.get(i).getName());
            System.out.println(notesListToString);
        }
         */
        List<String> notes = new ArrayList<>();
        notes.add("a");
        notes.add("b");

        // wybrana notatka do usuniecia
        lvNotesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(CalendarMain.this, notes.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        //------------------------------------------------------------------------------------------

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddNote();
            }
        });

        goToCurrentMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarOperations.selectedDate = LocalDate.now();
                setMonthView();
            }
        });

        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note.notesList.remove(Note.selectedNote);
                setMonthView();
            }
        });
    }

    private void setMonthView() {
        currentMonth.setText(monthYearFormatter(CalendarOperations.selectedDate));
        previousMonth.setText(monthYearFormatter(CalendarOperations.selectedDate.minusMonths(1)));
        nextMonth.setText(monthYearFormatter(CalendarOperations.selectedDate.plusMonths(1)));

        ArrayList<LocalDate> daysOfMonth = daysOfMonthMethod(CalendarOperations.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysOfMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);
        setNoteAdapter();

        // wroc do aktualnej daty
        if (CalendarOperations.selectedDate.getMonth().equals(LocalDate.now().getMonth()) &&
                CalendarOperations.selectedDate.getYear() == LocalDate.now().getYear()) {
            // niewidoczny, gdy jestesmy na aktualnym miesiacu
            goToCurrentMonth.setVisibility(View.INVISIBLE);
        } else {
            goToCurrentMonth.setVisibility(View.VISIBLE);
        }

        /*
        // usun notatke
        if (Note.selectedNote.equals(null)) {
            deleteNote.setVisibility(View.INVISIBLE);
        } else {
            deleteNote.setVisibility(View.VISIBLE);
        }
         */
    }

    // ----------------------------------------
    // zmiana miesiaca po kliknieciu przyciskow
    public void previousMonth(View view) {
        CalendarOperations.selectedDate = CalendarOperations.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonth(View view) {
        CalendarOperations.selectedDate = CalendarOperations.selectedDate.plusMonths(1);
        setMonthView();
    }
    // ----------------------------------------

    // wybrana data
    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarOperations.selectedDate = date;
            setMonthView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNoteAdapter();
    }

    private void setNoteAdapter() {
        ArrayList<Note> dayilyNotes = Note.notesForDate(CalendarOperations.selectedDate);
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), dayilyNotes);
        lvNotesList.setAdapter(noteAdapter);
    }

    private String[] fillListView() {

        // wczytanie z bazy danych
        List<Note> notesList = myDB.getAllNotes(currentUser);


        // zmiana na String
        String [] notesToString = new String[Note.notesList.size()];
        int i = 0;
        for (Note note : Note.notesList) {
            notesToString[i] = note.toString();
            i += 1;
        }

        // wypelnienie listView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesToString);
        lvNotesList.setAdapter(adapter);

        return notesToString;
    }

    public void openAddNote() {
        Intent intent = new Intent(this, AddNote.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}