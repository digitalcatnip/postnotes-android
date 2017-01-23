package io.catnip.postnotes.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Main class to interact with Realm
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

public class RealmManager {
    private static RealmManager sharedInstance = null;
    private static final String TAG = "PostNotesRealm";

    private Realm realm;
    private User mainUser;

    private RealmManager() {
        super();
        mainUser = null;
    }

    private RealmManager(Context context) {
        this();
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    /**
     * Get the current Realm Manager instance
     *
     * @param context - Only needs to be passed in at the start of the app.  Afterwards, can be null.
     * @return The global RealmManager instance
     */
    public static RealmManager getInstance(Context context) {
        if (sharedInstance == null) {
            sharedInstance = new RealmManager(context);
        }

        return sharedInstance;
    }

    /**
     * Save a model to Realm.  This will commit an object to the database
     * and then you will need to use the committed copy going forward.
     *
     * @param model The model to save
     * @return The committed copy that you should update - discard the old copy.
     */
    public RealmModel saveModel(RealmObject model) {
        getRealm().beginTransaction();
        model = getRealm().copyToRealm(model);
        getRealm().commitTransaction();
        return model;
    }

    /**
     * Save multiple models to Realm.  This will commit the objects to the database
     * and then you will need to use the committed copy going forward.
     *
     * @param models Models to save to Realm
     * @return The committed copies that you should update - discard the old copies.  Order cannot
     * be guaranteed from the original copy.
     */
    public ArrayList<RealmModel> saveModels(ArrayList<RealmObject> models) {
        getRealm().beginTransaction();
        List<RealmObject> copied = getRealm().copyToRealm(models);
        getRealm().commitTransaction();
        return new ArrayList(copied);
    }

    /**
     * Delete all models in the list from Realm
     *
     * @param models models to delete.
     */
    public void deleteModels(ArrayList<RealmObject> models) {
        getRealm().beginTransaction();
        for (RealmObject model: models) {
            model.deleteFromRealm();
        }
        getRealm().commitTransaction();
    }

    /**
     * Start a new query against the realm database.
     *
     * @param cl Class instance we're querying - i.e. T.class
     * @param <T> Class to run a query for
     * @return The beginning of a realm query which you can chain on
     */
    public <T extends RealmModel> RealmQuery<T> query(Class<T> cl) {
        return getRealm().where(cl);
    }

    /**
     * Set the main user of the app - for authentication purposes
     * @param u The user to set as the main user of the app
     */
    public void setMainUser(User u) {
        mainUser = u;
    }

    /**
     * Get the main user of the app
     * @return The main user
     */
    public User getMainUser() {
        return mainUser;
    }

    /**
     * Get the Realm database in order to support transactions
     * @return
     */
    public Realm getRealm() {
        return realm;
    }

    /**
     * Indicate whether we have a main user - that is, someone has logged in to the app.
     * @return
     */
    public boolean hasMainUser() {
        return mainUser != null;
    }

    private void initializeMainUser(final Context context) {
        RealmResults<User> users = query(User.class).findAllSorted("id", Sort.ASCENDING);
        if (users.size() > 0) {
            mainUser = users.first();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            try {
                auth.getCurrentUser().getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        try {
                            GetTokenResult result = task.getResult();
                            mainUser.setAuthToken(result.getToken());
                            Log.d(TAG, "Set user auth token to " + mainUser.getAuthToken());
                            NetworkManager.getSharedInstance().setAuthToken(mainUser.getAuthToken());
                            NetworkManager.getSharedInstance().initialize(context);
                        } catch (Exception e) {
                            Log.e(RealmManager.TAG, "Unable to fetch token from Firebase (no network?): " +
                                    e.getLocalizedMessage());
                        }
                    }
                });
            } catch(NullPointerException e) {
                Log.e(RealmManager.TAG, "Null pointer exception when fetching auth token for main user");
            }
        }
    }

    /**
     * Initialize our internal state from Realm.  This should only run when launching the app
     */
    public void initialize(Context context) {
        this.initializeMainUser(context);
    }
}
