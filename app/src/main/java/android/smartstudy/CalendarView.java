package android.smartstudy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView daysOfMonth;
    private final CalendarAdapter.OnItemListener listener;

    public CalendarView(@NonNull View itemView, CalendarAdapter.OnItemListener listener, ArrayList<LocalDate> days) {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        daysOfMonth = this.itemView.findViewById(R.id.calendarCell);
        this.listener = listener;
        this.days = days;
        this.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
