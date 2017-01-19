package io.catnip.postnotes.models;

import io.realm.RealmObject;

/**
 * Represents a user's note in Realm
 *
 * Created by james on 1/11/17.
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

public class Note extends RealmObject {
    private String note;
    private int id; //unique key

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Finds the next unique identifier in Realm. We don't need this after adding networking.
     *
     * @return The next unused ID in Realm
     */
    public int uniqueID() {
        return 0;
    }

    @Override
    public String toString() {
        return "Note " + id;
    }

    /**
     * Get the contents for this note
     * @return the contents of the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Set the note's contents
     * @param note The new contents
     */
    public void setNote(String note) {
        this.note = note;
    }
}
