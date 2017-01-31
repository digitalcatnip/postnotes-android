package io.catnip.postnotes.models;

import io.catnip.postnotes.R;
import android.content.Context;

import java.util.List;

import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Singleton class that manages network interactions.
 *
 * Created by james on 1/20/17.
 */

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

public class NetworkManager {
    private String authToken;
    private PostNotesService pnService;

    private static final String URL = "https://post-notes.appspot.com/";
    private static NetworkManager sharedInstance;

    private NetworkManager() {
    }

    public static NetworkManager getSharedInstance() {
        if(sharedInstance == null) {
            sharedInstance = new NetworkManager();
        }

        return sharedInstance;
    }

    /**
     * Set the JWT token from Firebase so Post Notes knows who we are
     * @param authToken
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Initialize the Retrofit configuration for Post Notes
     *
     * @param context Current context so we can load the api_url from strings.xml
     */
    public void initialize(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pnService = retrofit.create(PostNotesService.class);
    }

    /**
     * Get the list of all notes created by this user on the server
     *
     * @param callback Callback to handle the network response
     */
    public void getNotes(Callback<List<Note>> callback) {
        //TODO - Implement this - use pnService to make the call
        //The callback will be called when the network API returns
        //The callback should load the notes into realm and have the NoteListActivity reload
        //Remember to pass the authToken as the authorization header
    }

    /**
     * Create a new note on the server
     * @param newNote Note to create
     * @param callback Callback to handle the network response
     */
    public void createNote(Note newNote, Callback<Note> callback) {
        //TODO - Implement this - use pnService to make the call
        //The callback will be called when the network API returns
        //The callback should save the note to Realm and have the NoteListActivity reload
        //Remember to pass the authToken as the authorization header
    }
}
