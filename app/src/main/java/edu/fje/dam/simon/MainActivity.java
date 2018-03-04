package edu.fje.dam.simon;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import edu.fje.dam.simon.SimonView.SimonActivity;

/**
 * Actividad principal de la aplicación
 * Muestra una pantalla de carga con un animación por XML
 */
public class MainActivity extends AppCompatActivity {
    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    // logo de la aplicación
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Animacion XML, al acabar la animción passa a la siguiente actividad
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goWelcomeActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logo = (ImageView) findViewById(R.id.logo);
        logo.startAnimation(animation);
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
