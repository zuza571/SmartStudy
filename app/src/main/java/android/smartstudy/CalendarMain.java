package android.smartstudy;

import static android.smartstudy.CalendarOperations.monthYearFormatter;
import static android.smartstudy.CalendarOperations.fillCalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarMain extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView currentMonth, previousMonth, nextMonth;
    private RecyclerView recyclerView; // okno z wszystkimi dniami
    private ListView lvNotesList;
    private Button goToCurrentMonth;
    private String login;
    private List<String> currentUserData;
    private static User currentUser;
    static DataBaseHelper myDB;
    static List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        recyclerView = findViewById(R.id.recyclerView);
        currentMonth = findViewById(R.id.currentMonth);
        previousMonth = findViewById(R.id.previousMonthButton);
        nextMonth = findViewById(R.id.nextMonthButton);
        goToCurrentMonth = findViewById(R.id.goToCurrentMonth);
        lvNotesList = findViewById(R.id.lvNotesList);
        Button deleteNote = findViewById(R.id.deleteNote);
        Button addNote = findViewById(R.id.addNoteButton);

        myDB = new DataBaseHelper(CalendarMain.this);
        currentUser = new User();
        currentUserData = new ArrayList<>();

        // wczytanie currentUser
        Bundle bundle = getIntent().getExtras();
        login = bundle.getString("Login");
        currentUserData.addAll(myDB.current_user_data(login));
        currentUser.current_user(currentUser, currentUserData);

        // wszystkie notatki z bazy danych
        notesList = myDB.getAllNotes(currentUser);

        CalendarOperations.selectedDate = LocalDate.now();

        setMonthView();

        // wybrana notatka (do usuniecia)
        lvNotesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Note.selectedNote = String.valueOf(adapterView.getItemAtPosition(position));
                setMonthView();
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddNote();
            }
        });

        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesList = myDB.deleteNote(Note.selectedNote, notesList, currentUser);
                setMonthView();

            }
        });

        goToCurrentMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarOperations.selectedDate = LocalDate.now();
                setMonthView();
            }
        });
    }

    public void setMonthView() {
        currentMonth.setText(monthYearFormatter(CalendarOperations.selectedDate));
        previousMonth.setText(monthYearFormatter(CalendarOperations.selectedDate.minusMonths(1)));
        nextMonth.setText(monthYearFormatter(CalendarOperations.selectedDate.plusMonths(1)));

        ArrayList<LocalDate> daysOfMonth = fillCalendar(CalendarOperations.selectedDate);

        fillListView();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysOfMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);

        // wroc do aktualnej daty
        if (CalendarOperations.selectedDate.getMonth().equals(LocalDate.now().getMonth()) &&
                CalendarOperations.selectedDate.getYear() == LocalDate.now().getYear()) {
            // niewidoczny, gdy jestesmy na aktualnym miesiacu
            goToCurrentMonth.setVisibility(View.INVISIBLE);
        } else {
            goToCurrentMonth.setVisibility(View.VISIBLE);
        }
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

    // wybrana data - przypisanie
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
        fillListView();
    }

    // zmiana notatek na String i wypelnienie listView
    private void fillListView() {
        List<Note> dailyNotes = new ArrayList<>();

        for (int i = 0; i < notesList.size(); i++) {
            if (CalendarOperations.selectedDate.equals(notesList.get(i).getDate())) {
                dailyNotes.add(notesList.get(i));
            }
        }

        // zmiana na String
        String [] notesToString = new String[dailyNotes.size()];

        int i = 0;
        for (Note note : dailyNotes) {
            notesToString[i] = note.getText();
            i += 1;
        }

        // ustawienie adaptera notatki
        NoteAdapter adapter = new NoteAdapter(this, notesToString);
        lvNotesList.setAdapter(adapter);
    }

    public void openAddNote() {
        Intent intent = new Intent(this, AddNote.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login", login);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}