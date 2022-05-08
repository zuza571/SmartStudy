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
import java.time.format.TextStyle;
import java.util.ArrayList;

public class Kalendarz extends AppCompatActivity implements KalendarzAdapter.OnItemListener {
    private TextView aktualnyMiesiac;
    private TextView poprzedniMiesiac;
    private TextView nastepnyMiesiac;
    private RecyclerView recyclerView; // okno z wszystkimi dniami
    private LocalDate wybranaData;
    private TextView komorka_kalendarza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalendarz);
        initWidgets();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wybranaData = LocalDate.now();

        }
        setWidokMiesiaca();
    }

    // pobranie z widokow
    private void initWidgets() {
        recyclerView = findViewById(R.id.recyclerView);
        aktualnyMiesiac = findViewById(R.id.aktualnyMiesiac);
        poprzedniMiesiac = findViewById(R.id.poprzedniMiesiacButton);
        nastepnyMiesiac = findViewById(R.id.nastepnyMiesiacButton);
    }

    private void setWidokMiesiaca() {
        aktualnyMiesiac.setText(data(wybranaData));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            poprzedniMiesiac.setText(data(wybranaData.minusMonths(1)));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nastepnyMiesiac.setText(data(wybranaData.plusMonths(1)));
        }
        ArrayList<String> dniMiesiaca = dniMiesiacaLista(wybranaData);

        KalendarzAdapter kalendarzAdapter = new KalendarzAdapter(dniMiesiaca, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kalendarzAdapter);
    }

    private ArrayList<String> dniMiesiacaLista(LocalDate data) {
        ArrayList<String> dniMiesiacaLista = new ArrayList<>();
        YearMonth yearMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            yearMonth = YearMonth.from(data);
        }

        int dniMiesiaca = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dniMiesiaca = yearMonth.lengthOfMonth();
        }

        LocalDate pierwszyMiesiac = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            pierwszyMiesiac = wybranaData.withDayOfMonth(1);
        }

        int dzienTygodnia = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dzienTygodnia = pierwszyMiesiac.getDayOfWeek().getValue();
        }

        for (int i=1; i<=42; i++) {
            if(i <= dzienTygodnia || i > dniMiesiaca + dzienTygodnia)
            {
                dniMiesiacaLista.add("");
            } else {
                dniMiesiacaLista.add(String.valueOf(i - dzienTygodnia));
            }
        }

        return dniMiesiacaLista;
    }

    // zmiana daty na String
    private String data(LocalDate data) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            return data.format(formatter);
        } else
            return "";
    }

    // ----------------------------------------
    // zmiana miesiaca po kliknieciu przyciskow
    public void poprzedniMiesiac(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wybranaData = wybranaData.minusMonths(1);
        }
        setWidokMiesiaca();
    }

    public void nastepnyMiesiac(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wybranaData = wybranaData.plusMonths(1);
        }
        setWidokMiesiaca();
    }
    // ----------------------------------------

    // wyskakujace okienko z wybrana data
    @Override
    public void onItemClick(int pozycja, String dzien) {
        if (!dzien.equals("")) {
            String tekst = "Wybrana Data: " + dzien + " " + data(wybranaData);
            Toast.makeText(this, tekst, Toast.LENGTH_LONG).show();
        }

    }
}