package edu.fje.dam.simon.Services;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.fje.dam.simon.Models.Player;
import edu.fje.dam.simon.R;

/**
 * Created by sava on 4/03/18.
 */

public class FireBaseIntentService extends IntentService {
    DatabaseReference Simon;

    public FireBaseIntentService() {
        super("firebaseService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Simon = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null) {
            String nom = intent.getStringExtra("nom");
            String punts = intent.getStringExtra("punts");

            afegirPlayer(nom, punts);

        }
        return START_NOT_STICKY;
    }

    private void afegirPlayer(String Nom, String Punts) {
        String nom = Nom;
        int punts = (Integer.parseInt(Punts));

        if (!TextUtils.isEmpty(nom)) {

            //mRef =  FirebaseDatabase.getInstance().getReferenceFromUrl("xxxxxxxxxxxxxxxxxxxxxx");
            //Simon = mRef.child("Name");

            String id = Simon.push().getKey();

            Player player = new Player(nom, punts);

            Simon.child(id).setValue(player);

            //this.editTextNom.setText("");

        } else {
            Toast.makeText(this, "Cal entrar un nom", Toast.LENGTH_LONG).show();
        }
    }
}
