package edu.fje.dam.simon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import edu.fje.dam.simon.Adapters.ImageAdapter;
import edu.fje.dam.simon.Fragments.Main_Fragment;
import edu.fje.dam.simon.Models.Game;
import edu.fje.dam.simon.Models.Player;
import edu.fje.dam.simon.Services.AudioIntentService;
import edu.fje.dam.simon.Services.AudioTaskctivity;
import edu.fje.dam.simon.Utils.SoundsUtility;

/**
 * Actividad donde se desarrola el juego
 */
public class TableActivity extends AudioTaskctivity {
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    private String LOG = "edu.fje.dam2";
    private Intent intent;
    // variable que comprueba si la música de fondo se reproduce
    private boolean isReproduint= true;
    ArrayAdapter<Image> Adapter;
    // tablero donde se muestran las figuras
    GridView tableGrid;
    // contexto de la actividad
    private Context context;
    // imagen generada de forma aleatoria
    private ImageView randomImage;
    // sonido de error
    private MediaPlayer errorSound;
    // Instancia de la clase Player
    private Player p;
    // Instancia de la clase Game
    private Game g;
    // String que guarda el nombre y puntuación del jugador
    private String nomPunts;
    // Array con lo sonidos para cada figura
    public int sounds[] = {
            R.raw.button1,
            R.raw.button2,
            R.raw.button3,
            R.raw.button4,
            R.raw.button5,
            R.raw.button6,
            R.raw.button7,
            R.raw.button8,
            R.raw.button9,
            R.raw.button10,
            R.raw.button11,
            R.raw.button12

    };
    // lista que almacena las animación para repetir la sequencia de figuras
    private static List<Animator> animations = new ArrayList<Animator>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        p = new Player("aaorn",0);
        g = new Game(p);

        context = getApplicationContext();
        tableGrid = (GridView) findViewById(R.id.table);

        errorSound= MediaPlayer.create(this, R.raw.sfx_sounds_error1);


        randomImage = (ImageView) findViewById(R.id.randomImage);

        //changeImage();
        // generamos la primera imagen aleatoria
        randomImage.setImageResource(g.changeImage());
        // la hacemos aparacer
        fadeImage(3000,1,false);
        // utilizmos un adapter personalizado para mostrar las figuras en la actividad
        tableGrid.setAdapter(new ImageAdapter(context,g.images));

        // recuperamos información de la activity anterior
        Intent intent = getIntent();
        String missatge = intent.getStringExtra(WelcomeActivity.EXTRA_MISSATGE);
        g.getPlayer().setName(missatge);
        g.getPlayer().getPoints();

        Toast.makeText(this, g.getPlayer().getName(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, g.getPlayer().getPoints(), Toast.LENGTH_SHORT).show();

        //nomPunts = "" + g.getPlayer().getName() +" " + g.getPlayer().getPoints();
        // evento que detecta un click sobre una figura
        tableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                // añadimos una figura a la lista de respuestas del jugador
                g.imagesSelected.add(position);

                if(g.checkResponse()) {
                    sound(position);
                    if(g.imagesSelected.size() == g.lastImages.size()) {
                        Log.d("SAVA", "OK");
                        g.setTurno(g.getTurno()+1);
                        g.getPlayer().setPoints(g.getTurno());
                        showResponses();
                    }
                }else {
                    Log.d("SAVA", "MAL");
                    errorSound.start();
                    task.onProgressUpdate("pausa");
                    //SoundsUtility.playSound(SoundsUtility.Type.ERROR,context);
                    nomPunts = "" + g.getPlayer().getName() +", " + g.getPlayer().getPoints();
                    goEndActivity();
                }
            }
        });

    }

    /**
     * Método que reproduce un sonido para cada figura
     * @param position
     */
    private void sound(int position) {
        MediaPlayer sound;
        sound = MediaPlayer.create(this, sounds[position]);
        sound.start();
    }

    /**
     * Método que crea una lista de animaciónes y reproduce la sequancia de figuras
     * de forma sequencial.
     */
    private void showResponses() {
        g.changeImage();

        Log.d("SAVA", "SHOW RESPONSES");
        Log.d("SAVA", g.lastImages.toString());
        AnimatorSet s = new AnimatorSet();
        animations.clear();

        for (int i = 0; i < g.lastImages.size(); i++) {
            final int finalI = i;

            ObjectAnimator anim =
                    //ObjectAnimator.ofArgb(tableGrid.getChildAt(g.lastImages.get(i)),"BackgroundColor",Color.DKGRAY);
                    //ObjectAnimator.ofFloat(tableGrid.getChildAt(g.imagesSelected.get(i)),"Alpha",0.5f);
                    ObjectAnimator.ofFloat(randomImage,"Alpha",1);
            anim.setDuration(1000);
            anim.setStartDelay(2000);

            anim.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animator) {
                    //tableGrid.getChildAt(g.lastImages.get(finalI)).setBackgroundColor(Color.DKGRAY);
                    //tableGrid.getChildAt(g.imagesSelected.get(finalI)).setAlpha(0.5f);
                    randomImage.setImageResource(g.images[g.lastImages.get(finalI)]);
                    randomImage.setAlpha(0.0f);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    //tableGrid.getChildAt(g.lastImages.get(finalI)).setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    //tableGrid.getChildAt(g.imagesSelected.get(finalI)).setAlpha(1);
                    randomImage.setAlpha(0.0f);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            //anim.start();
            animations.add(anim);
        }
        s.playSequentially(animations);
        s.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                g.imagesSelected.clear();
            }
        });
        s.start();

        //randomImage.setAlpha(0.0f);
        //fadeImage(1000,1,true);
    }

    /**
     * Método que aplica un efecto de fade sobre una imagen
     * @param duration
     * @param alpha
     * @param callback
     */
    private void fadeImage(int duration, int alpha, final boolean callback) {
        for(int i = 0; i < g.lastImages.size();i++) {
            randomImage.setImageResource(g.images[g.lastImages.get(i)]);
            Log.d("SAVA", "SHOW image "+i);
            randomImage.animate().setDuration(duration).alpha(alpha)
                    .withStartAction(new Runnable() {
                        @Override
                        public void run() {
                            if (callback) {
                                // changeImage();
                                //randomImage.setImageResource(g.changeImage());
                            }
                        }
                    });
        }
    }

    /**
     * recuperamos el menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * funcion que se utiliza para gestionar las opciones del menú.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.music_settings:
                if (!isReproduint) {
                    //intent.putExtra("operacio", "inici");
                    //startService(intent);
                    task.onProgressUpdate("inici");
                } else {
                    //intent.putExtra("operacio", "pausa");
                    //startService(intent);
                    task.onProgressUpdate("pausa");
                }

                isReproduint = !isReproduint;

                return  true;
            case R.id.help:
                Intent intent = new Intent(this, Main_Fragment.class);
                startActivity(intent);

                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * realizamos un intent y passamos el nombre del jugador
     */
    public void goEndActivity(){
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra(EXTRA_MISSATGE, nomPunts);
        startActivity(intent);
    }

}
