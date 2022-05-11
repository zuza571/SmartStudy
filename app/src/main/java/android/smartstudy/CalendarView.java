package android.smartstudy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CalendarView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView daysOfMonth;
    private final CalendarAdapter.OnItemListener listener;

    public CalendarView(@NonNull View itemView, CalendarAdapter.OnItemListener listener) {
        super(itemView);
        this.listener = listener;
        daysOfMonth = this.itemView.findViewById(R.id.calendarCell);
        this.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(getAdapterPosition(), (String) daysOfMonth.getText());
    }
}
