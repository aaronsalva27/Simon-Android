package edu.fje.dam.simon;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import edu.fje.dam.simon.SimonView.SimonActivity;

public class MainActivity extends AppCompatActivity {
    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //goSimonActivity();
                goWelcomeActivity();
            }
        }, SPLASH_SCREEN_DELAY);

        setContentView(R.layout.activity_main);
    }

    /**
     * realizamos un intent y passamos el nombre del jugador
     */
    public void goSimonActivity(){
        Intent intent = new Intent(this, SimonActivity.class);
        intent.putExtra(EXTRA_MISSATGE, "" );
        startActivity(intent);
    }

    public void goWelcomeActivity(){
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(EXTRA_MISSATGE, "" );
        startActivity(intent);
    }
}
