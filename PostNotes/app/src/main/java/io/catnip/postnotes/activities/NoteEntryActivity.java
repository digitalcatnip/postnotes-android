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
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.catnip.postnotes.R;

public class NoteEntryActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "PostNotesEntry";
    public static final String NEW_NOTE = "new_note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_note_entry);

        //Use the funny font in the title
        TextView title = (TextView)findViewById(R.id.note_entry_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "LuckiestGuy.ttf");
        title.setTypeface(font);

        //Use the funny font on the button
        Button entry = (Button)findViewById(R.id.note_entry_button);
        entry.setTypeface(font);
        entry.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "Note entry complete");

        //The user entered their text, so let's send it back to the list
        EditText text = (EditText)findViewById(R.id.note_entry_text);

        Intent intent = new Intent();
        intent.putExtra(NEW_NOTE, text.getText().toString());
        setResult(0, intent);
        finish();
    }
}
