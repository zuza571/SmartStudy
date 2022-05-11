package android.smartstudy;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Calendar extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView currentMonth, previousMonth, nextMonth, calendar_cell;
    private RecyclerView recyclerView; // okno z wszystkimi dniami
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        recyclerView = findViewById(R.id.recyclerView);
        currentMonth = findViewById(R.id.currentMonth);
        previousMonth = findViewById(R.id.previousMonthButton);
        nextMonth = findViewById(R.id.nextMonthButton);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now();
        }
        setMonthView();
    }

    private void setMonthView() {
        currentMonth.setText(dateFormatter(selectedDate));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            previousMonth.setText(dateFormatter(selectedDate.minusMonths(1)));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nextMonth.setText(dateFormatter(selectedDate.plusMonths(1)));
        }
        ArrayList<String> daysOfMonth = daysOfMonthListMethod(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysOfMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysOfMonthListMethod(LocalDate date) {
        ArrayList<String> daysOfMonthList = new ArrayList<>();
        YearMonth yearMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            yearMonth = YearMonth.from(date);
        }

        int daysOfMonth = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            daysOfMonth = yearMonth.lengthOfMonth();
        }

        LocalDate firstMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            firstMonth = selectedDate.withDayOfMonth(1);
        }

        int dayOfWeek = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dayOfWeek = firstMonth.getDayOfWeek().getValue();
        }

        for (int i=1; i<=42; i++) {
            if(i <= dayOfWeek || i > daysOfMonth + dayOfWeek)
            {
                daysOfMonthList.add("");
            } else {
                daysOfMonthList.add(String.valueOf(i - dayOfWeek));
            }
        }

        return daysOfMonthList;
    }

    // zmiana daty na String
    private String dateFormatter(LocalDate date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            return date.format(formatter);
        } else
            return "";
    }

    // ----------------------------------------
    // zmiana miesiaca po kliknieciu przyciskow
    public void previousMonth(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.minusMonths(1);
        }
        setMonthView();
    }

    public void nextMonth(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.plusMonths(1);
        }
        setMonthView();
    }
    // ----------------------------------------

    // wyskakujace okienko z wybrana data
    @Override
    public void onItemClick(int position, String day) {
        if (!day.equals("")) {
            String text = "Wybrana Data: " + day + " " + dateFormatter(selectedDate);
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

    }
}