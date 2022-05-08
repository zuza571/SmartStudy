package android.smartstudy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public KalendarzWidok onCreateViewHolder(@NonNull ViewGroup rodzic, int typWidoku) {
        LayoutInflater inflater = LayoutInflater.from(rodzic.getContext());
        View widok = inflater.inflate(R.layout.komorka_kalendarza, rodzic, false);
        ViewGroup.LayoutParams layoutParams = widok.getLayoutParams();
        layoutParams.height = (int) (rodzic.getHeight() * 0.166666666);
        return new KalendarzWidok(widok, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull KalendarzWidok kalendarzWidok, int pozycja) {
        kalendarzWidok.dniMiesiaca.setText(dniMiesiaca.get(pozycja));
    }

    @Override
    public int getItemCount() {
        return dniMiesiaca.size();
    }
    public interface OnItemListener {
        void onItemClick(int pozycja, String dzien);
    }
}
