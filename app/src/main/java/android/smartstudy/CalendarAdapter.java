package android.smartstudy;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarView> {
    private final ArrayList<LocalDate> days;
    private final OnItemListener listener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener listener) {
        this.days = days;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CalendarView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // pojedyncza komorka
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.13);

        return new CalendarView(view, listener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarView calendarView, int position) {
        // zaznaczenie wybranej daty
        final LocalDate date = days.get(position);

        if(date == null)
            calendarView.daysOfMonth.setText("");
        else {
            calendarView.daysOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarOperations.selectedDate))
                calendarView.parentView.setBackgroundColor(Color.parseColor("#CCE5FF"));
            // aktualna data
            else if(date.equals(LocalDate.now()))
                calendarView.parentView.setBackgroundColor(Color.parseColor("#A0A0A0"));
            // jesli jest notatka - inny kolor

        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }
}
