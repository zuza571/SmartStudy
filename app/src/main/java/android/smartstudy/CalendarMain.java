package android.smartstudy;

import static android.smartstudy.CalendarOperations.monthYearFromDate;
import static android.smartstudy.CalendarOperations.daysOfMonthMethod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarMain extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView currentMonth, previousMonth, nextMonth;
    private RecyclerView recyclerView; // okno z wszystkimi dniami
    private ListView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        recyclerView = findViewById(R.id.recyclerView);
        currentMonth = findViewById(R.id.currentMonth);
        previousMonth = findViewById(R.id.previousMonthButton);
        nextMonth = findViewById(R.id.nextMonthButton);
        notesList = findViewById(R.id.notesList);

        CalendarOperations.selectedDate = LocalDate.now();
        setMonthView();
    }

    private void setMonthView() {
        currentMonth.setText(monthYearFromDate(CalendarOperations.selectedDate));
        previousMonth.setText(monthYearFromDate(CalendarOperations.selectedDate.minusMonths(1)));
        nextMonth.setText(monthYearFromDate(CalendarOperations.selectedDate.plusMonths(1)));

        ArrayList<LocalDate> daysOfMonth = daysOfMonthMethod(CalendarOperations.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysOfMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);
        setNoteAdapter();
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
        notesList.setAdapter(noteAdapter);
    }

    public void newNote(View view) {
        startActivity(new Intent(this, EditNote.class));
    }

    public void currentMonth(View view) {
        CalendarOperations.selectedDate = LocalDate.now();
        setMonthView();
    }
}