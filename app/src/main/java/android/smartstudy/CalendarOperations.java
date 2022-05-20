package android.smartstudy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarOperations {
    public static LocalDate selectedDate, firstOfMonth;

    // formatowanie daty
    public static String dateFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    // formatowanie daty na miesiac i rok - do widoku
    public static String monthYearFormatter(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String dayFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E");
        return date.format(formatter);
    }

    public static String timeFormatter(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    // podzial miesiaca na poszczegolne dni
    public static ArrayList<LocalDate> fillCalendar(LocalDate date) {
        ArrayList<LocalDate> daysOfMonth = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        // ile dni w miesiacu
        int monthLength = yearMonth.lengthOfMonth();

        // cos sie przestawilo o 1 dzien, wiec minusDays
        firstOfMonth = CalendarOperations.selectedDate.withDayOfMonth(1).minusDays(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i=1; i<=42; i++) {
            // dzien rozpoczecia i zakonczenia miesiaca we wlasciwym miejscu
            if(i <= dayOfWeek || i > monthLength + dayOfWeek)
                daysOfMonth.add(null);
            else
                daysOfMonth.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek));
        }
        return daysOfMonth;
    }

}
