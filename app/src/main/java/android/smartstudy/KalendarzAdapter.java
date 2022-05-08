package android.smartstudy;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.ArrayList;

public class KalendarzAdapter extends RecyclerView.Adapter<KalendarzWidok> {
    private final ArrayList<String> dniMiesiaca;

    private final OnItemListener listener;

    public KalendarzAdapter(ArrayList<String> dniMiesiaca, OnItemListener listener) {
        this.dniMiesiaca = dniMiesiaca;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KalendarzWidok onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // pojedyncza komorka
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.komorka_kalendarza, parent, false);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.13);

        return new KalendarzWidok(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull KalendarzWidok kalendarzWidok, int pozycja) {
        // zaznaczenie wybranej daty
        //final LocalDate data = dniMiesiaca.get(pozycja)

        kalendarzWidok.dniMiesiaca.setText(dniMiesiaca.get(pozycja));

        //kalendarzWidok.parentView.setBackgroundColor(Color.LTGRAY);

    }

    @Override
    public int getItemCount() {
        return dniMiesiaca.size();
    }

    public interface OnItemListener {
        void onItemClick(int pozycja, String dzien);
    }
}
