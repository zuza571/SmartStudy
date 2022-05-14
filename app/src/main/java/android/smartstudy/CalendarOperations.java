package android.smartstudy;

import java.time.LocalDate;
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

    // podzial miesiaca na poszczegolne dni
    public static ArrayList<LocalDate> daysOfMonthMethod(LocalDate date) {
        ArrayList<LocalDate> daysOfMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        // ile dni w miesiacu
        int daysOfMonth = yearMonth.lengthOfMonth();

        firstOfMonth = CalendarOperations.selectedDate.withDayOfMonth(1).minusDays(1); // cos sie przestawilo o 1 dzien, wiec minusDays
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i=1; i<=42; i++) {
            if(i <= dayOfWeek || i > daysOfMonth + dayOfWeek)
                daysOfMonthArray.add(null);
            else
                daysOfMonthArray.add(LocalDate.of(selectedDate.getYear(),
                        selectedDate.getMonth(), i - dayOfWeek));
        }
        return daysOfMonthArray;
    }

}
