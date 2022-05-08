package android.smartstudy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class KalendarzWidok extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView dniMiesiaca;
    private final KalendarzAdapter.OnItemListener listener;

    public KalendarzWidok(@NonNull View widok, KalendarzAdapter.OnItemListener listener) {
        super(widok);
        this.listener = listener;
        dniMiesiaca = itemView.findViewById(R.id.komorkaKalendarza);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View widok_) {
        listener.onItemClick(getAdapterPosition(), (String) dniMiesiaca.getText());
    }
}
