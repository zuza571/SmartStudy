package android.smartstudy;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<LocalDate> days;
    private final OnItemListener listener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener listener) {
        this.days = days;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // pojedyncza komorka
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.13);

        return new CalendarViewHolder(view, listener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder calendarViewHolder, int position) {
        // zaznaczenie wybranej daty
        final LocalDate date = days.get(position);

        if(date == null)
            calendarViewHolder.dayOfMonth.setText("");
        else {
            calendarViewHolder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarOperations.selectedDate))
                calendarViewHolder.parentView.setBackgroundColor(Color.parseColor("#CCE5FF"));
            // aktualna data
            else if(date.equals(LocalDate.now()))
                calendarViewHolder.parentView.setBackgroundColor(Color.parseColor("#A0A0A0"));
            // jesli jest notatka w danym dniu
            else {
                for (int i = 0; i < 42; i++) {
                    for (int j = 0; j < CalendarMain.notesList.size(); j++) {
                        LocalDate noteDate = CalendarMain.notesList.get(j).getDate();
                        if (date.equals(noteDate)) {
                            calendarViewHolder.parentView.setBackgroundColor(Color.parseColor("#FFCC99"));
                            break;
                        }
                    }
                }
            }
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
