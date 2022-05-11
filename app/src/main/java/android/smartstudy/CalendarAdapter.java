package android.smartstudy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarView> {
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener listener;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener listener) {
        this.daysOfMonth = daysOfMonth;
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

        return new CalendarView(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarView calendarView, int position) {
        // zaznaczenie wybranej daty
        calendarView.daysOfMonth.setText(daysOfMonth.get(position));

    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String day);
    }
}
