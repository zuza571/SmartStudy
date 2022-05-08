package android.smartstudy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class KalendarzWidok extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView dniMiesiaca;
    private final KalendarzAdapter.OnItemListener listener;

    public KalendarzWidok(@NonNull View itemView, KalendarzAdapter.OnItemListener listener) {
        super(itemView);
        this.listener = listener;

        dniMiesiaca = this.itemView.findViewById(R.id.komorkaKalendarza);
        this.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(getAdapterPosition(), (String) dniMiesiaca.getText());
    }
}
