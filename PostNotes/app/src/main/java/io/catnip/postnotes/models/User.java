package io.catnip.postnotes.models;

import com.google.firebase.auth.FirebaseUser;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Represents a user in Realm / Firebase
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

public class User extends RealmObject {
    private String authID;
    private String email;
    private String pictureURL;
    private String name;
    private String authToken;
    private int id; //unique key

    public User() {
        authID = email = pictureURL = name = "";
    }

    public User(FirebaseUser user) {
        this();
        setAuthID(user.getUid());
        setEmail(user.getEmail());
        setPictureURL(user.getPhotoUrl().toString());
        setName(user.getDisplayName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the Firebase ID for this user
     * @return
     */
    public String getAuthID() {
        return authID;
    }

    /**
     * Set the Firebase ID for this user
     * @param authID The new ID
     */
    public void setAuthID(String authID) {
        this.authID = authID;
    }

    /**
     * Get the user's email address
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user's email address
     * @param email The new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the URL of the user's profile picture from Firebase
     * @return
     */
    public String getPictureURL() {
        return pictureURL;
    }

    /**
     * Set the user's profile picture URL
     * @param pictureURL the new URL
     */
    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    /**
     * Get the next unique ID in Realm for users
     * @return
     */
    public static int getNextID() {
        RealmQuery<User> q = RealmManager.getInstance(null).query(User.class);
        RealmResults<User> users = q.findAllSorted("id", Sort.DESCENDING);
        if (users.size() > 0)
            return users.first().getId() + 1;
        return 100;
    }

    /**
     * Get the user's full name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the user's full name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the user's auth token - usually a JWT
     * @return
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Set the user's auth token from Firebase - it's usually a JWT
     * @param authToken
     */
    public void setAuthToken(String authToken) {
        RealmManager.getInstance(null).getRealm().beginTransaction();
        this.authToken = authToken;
        RealmManager.getInstance(null).getRealm().commitTransaction();
    }
}
