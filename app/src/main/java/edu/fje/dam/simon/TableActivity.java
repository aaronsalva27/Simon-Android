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
import edu.fje.dam.simon.Models.Game;
import edu.fje.dam.simon.Models.Player;
import edu.fje.dam.simon.Services.AudioIntentService;
import edu.fje.dam.simon.Services.AudioTaskctivity;
import edu.fje.dam.simon.Utils.SoundsUtility;


public class TableActivity extends AudioTaskctivity {
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    private String LOG = "edu.fje.dam2";
    private Intent intent;
    private boolean isReproduint= true;
    ArrayAdapter<Image> Adapter;
    GridView tableGrid;
    private Context context;
    private ImageView randomImage;
    private MediaPlayer errorSound;

    private Player p;
    private Game g;
    private String nomPunts;

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

        randomImage.setImageResource(g.changeImage());

        fadeImage(3000,1,false);

        tableGrid.setAdapter(new ImageAdapter(context,g.images));

        // recuperamos información de la activity anterior
        Intent intent = getIntent();
        String missatge = intent.getStringExtra(WelcomeActivity.EXTRA_MISSATGE);
        g.getPlayer().setName(missatge);
        g.getPlayer().getPoints();

        Toast.makeText(this, g.getPlayer().getName(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, g.getPlayer().getPoints(), Toast.LENGTH_SHORT).show();

        //nomPunts = "" + g.getPlayer().getName() +" " + g.getPlayer().getPoints();

        tableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
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
                    //SoundsUtility.playSound(SoundsUtility.Type.ERROR,context);
                    nomPunts = "" + g.getPlayer().getName() +", " + g.getPlayer().getPoints();
                    goEndActivity();
                }
            }
        });

    }

    private void sound(int position) {
        MediaPlayer sound;
        sound = MediaPlayer.create(this, sounds[position]);
        sound.start();
    }

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
