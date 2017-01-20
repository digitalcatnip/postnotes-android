package io.catnip.postnotes.activities;

//  Copyright © 2017 Digital Catnip. All rights reserved.
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
                //If the plus button is pressed, show the note entry screen
                Intent intent = new Intent(NoteListActivity.this, NoteEntryActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //Start the list of notes to display.
        //This really should be read from Realm.
        //Also, we'll eventually call the network from here as well.
        notes = new ArrayList<>();

        //Configure the list to use whatever notes are put in notes array
        listAdapter = new NoteArrayAdapter(this, android.R.layout.list_content, notes);
        ListView list = (ListView)findViewById(R.id.note_list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Note n = notes.get(position);
        Log.i(TAG, "Clicked note: " + n.getNote());
        //You could modify the code to edit an existing note - exercise for the reader.
    }

    @Override
    public void onActivityResult(int unk1, int unk2, Intent data) {
        //The response on the activity will contain a new note.  Let's create it and display it.
        //The Realm persistence code will go here.
        String text = data.getStringExtra(NoteEntryActivity.NEW_NOTE);
        Log.i(TAG, "Creating note: " + text);

        Note n = new Note();
        n.setId(notes.size());
        n.setNote(text);
        notes.add(n);
    }
}
