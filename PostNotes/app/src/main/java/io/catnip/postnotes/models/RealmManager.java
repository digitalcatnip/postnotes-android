package io.catnip.postnotes.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by james on 1/11/17.
 */

public class RealmManager {
    private static RealmManager sharedInstance = null;

    private Realm realm;

    private RealmManager() {
        super();
    }

    private RealmManager(Context context) {
        this();
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    /**
     * Get the instance this is
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
        realm.beginTransaction();
        model = realm.copyToRealm(model);
        realm.commitTransaction();
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
        realm.beginTransaction();
        List<RealmObject> copied = realm.copyToRealm(models);
        realm.commitTransaction();
        return new ArrayList(copied);
    }

    /**
     * Delete all models in the list from Realm
     *
     * @param models models to delete.
     */
    public void deleteModels(ArrayList<RealmObject> models) {
        realm.beginTransaction();
        for (RealmObject model: models) {
            model.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    /**
     * Start a new query against the realm database.
     *
     * @param cl Class instance we're querying - i.e. T.class
     * @param <T> Class to run a query for
     * @return The beginning of a realm query which you can chain on
     */
    public <T extends RealmModel> RealmQuery<T> query(Class<T> cl) {
        return realm.where(cl);
    }
}
