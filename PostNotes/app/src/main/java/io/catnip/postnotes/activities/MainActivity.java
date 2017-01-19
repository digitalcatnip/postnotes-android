package io.catnip.postnotes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.catnip.postnotes.R;
import io.catnip.postnotes.models.RealmManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmManager.getInstance(this).initialize();
        if(!RealmManager.getInstance(null).hasMainUser()) {
            Intent intent = new Intent(this, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, NoteListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        finish();
    }
}
