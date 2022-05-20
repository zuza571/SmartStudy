package android.smartstudy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TimetableAdapter extends ArrayAdapter<String> {
    public TimetableAdapter(Context context, String[] lessons) {
        super(context, android.R.layout.simple_list_item_1, lessons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String lesson = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lesson_cell, parent, false);

        TextView lessonCell = convertView.findViewById(R.id.lessonCell);
        TextView timeCell = convertView.findViewById(R.id.lessonCellTime);
        TextView roomCell = convertView.findViewById(R.id.lessonCellRoom);
        RelativeLayout layout = convertView.findViewById(R.id.relativeLayout);

        if (convertView != null) {
            // porownujemy miejsce w pamieci, a nie tekst!
            if (lesson == Lesson.selectedLesson)
                layout.setBackgroundColor(Color.parseColor("#CCE5FF"));
        }

        String startTime = "";
        String endTime = "";
        String room = "";

        for (int i = 0; i < Timetable.lessons.size(); i++) {
            if(CalendarOperations.dayFormatter(CalendarOperations.selectedDate).equals(Timetable.lessons.get(i).getDayOfWeek())
                    && lesson.equals(Timetable.lessons.get(i).getText())) {
                room = Timetable.lessons.get(i).getRoom();
                startTime = CalendarOperations.timeFormatter(Timetable.lessons.get(i).getStartTime());
                int hours = Timetable.lessons.get(i).getDuration()/60;
                int minutes = Timetable.lessons.get(i).getDuration() - 60*hours;
                endTime = CalendarOperations.timeFormatter(Timetable.lessons.get(i).getStartTime().plusHours(hours).plusMinutes(minutes));
            }
        }
        String timeText = startTime + " - " + endTime;
        String roomText = "sala: " + room;

        lessonCell.setText(lesson);
        timeCell.setText(timeText);
        roomCell.setText(roomText);
        return convertView;
    }
}
