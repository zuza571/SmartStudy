package android.smartstudy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<String> {

    public NoteAdapter(Context context, String[] notes) {
        super(context, android.R.layout.simple_list_item_1, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String note = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);

        TextView noteCell = convertView.findViewById(R.id.noteCell);

        if (convertView != null) {
            if (note.equals(Note.selectedNote))
                noteCell.setBackgroundColor(Color.parseColor("#CCE5FF"));
        }

        noteCell.setText(note);
        return convertView;
    }
}
