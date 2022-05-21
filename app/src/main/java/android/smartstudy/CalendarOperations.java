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

    public static String dayFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E");
        return date.format(formatter);
    }

    // numer miesiaca - do widoku
    public static String monthFormatter(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("L");
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

    public static String polishMonths(LocalDate date) {
        String month = "";
        for (int i = 0; i < 12; i++) {
            String monthString = CalendarOperations.monthFormatter(date);
            switch (monthString) {
                case "1":
                    month = "Styczeń";
                    break;
                case "2":
                    month = "Luty";
                    break;
                case "3":
                    month = "Marzec";
                    break;
                case "4":
                    month = "Kwiecień";
                    break;
                case "5":
                    month = "Maj";
                    break;
                case "6":
                    month = "Czerwiec";
                    break;
                case "7":
                    month = "Lipiec";
                    break;
                case "8":
                    month = "Sierpień";
                    break;
                case "9":
                    month = "Wrzesień";
                    break;
                case "10":
                    month = "Październik";
                    break;
                case "11":
                    month = "Listopad";
                    break;
                case "12":
                    month = "Grudzień";
                    break;
                default:
                    break;
            }
        }
        String monthYear = month + " " + date.getYear();
        return monthYear;
    }

}
