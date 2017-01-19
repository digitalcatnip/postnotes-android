package io.catnip.postnotes.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import io.catnip.postnotes.R;
import io.catnip.postnotes.models.Note;
import io.catnip.postnotes.views.NoteArrayAdapter;

public class NoteListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String TAG = "PostNotesList";

    private NoteArrayAdapter listAdapter;
    private ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "FAB pressed!");
            }
        });

        notes = new ArrayList<>();

        Note n = new Note();
        n.setId(1);
        n.setNote("New note from james how does this look?");
        notes.add(n);

        n = new Note();
        n.setId(1);
        n.setNote("New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?New note from james how does this look?");
        notes.add(n);

        n = new Note();
        n.setId(1);
        n.setNote("This is the last note - it's all we need.");
        notes.add(n);

        listAdapter = new NoteArrayAdapter(this, android.R.layout.list_content, notes);
        ListView list = (ListView)findViewById(R.id.note_list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Note n = notes.get(position);
        Log.i(TAG, "Clicked note: " + n.getNote());
    }
}
