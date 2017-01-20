package io.catnip.postnotes.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.catnip.postnotes.models.Note;

/**
 * Controls the display of notes on the page
 *
 * Created by james on 1/19/17.
 */

public class NoteArrayAdapter extends ArrayAdapter {

    public NoteArrayAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        //This configures the row to show a specific note.
        //contentView may be recycled - if it is, just reuse it.  If it isn't, create it.
        TextView text;
        if (contentView != null) {
            text = (TextView) contentView;
        } else {
            text = new TextView(this.getContext());
            text.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            text.setPadding(0, 10, 0, 10);
        }

        //Get the note we need to display and then set the TextView text appropriately
        Note n = (Note) this.getItem(position);
        text.setText(n.getNote());
        return text;
    }
}
