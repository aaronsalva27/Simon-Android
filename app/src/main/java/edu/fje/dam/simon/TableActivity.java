package edu.fje.dam.simon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.fje.dam.simon.Adapters.ImageAdapter;
import edu.fje.dam.simon.Models.Game;
import edu.fje.dam.simon.Models.Player;
import edu.fje.dam.simon.Services.AudioIntentService;
import edu.fje.dam.simon.SimonView.SimonRandomFigureFragment;
import edu.fje.dam.simon.SimonView.SimonTableFragment;

public class TableActivity extends AppCompatActivity {
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    private Intent intent;
    private boolean isReproduint= true;
    ArrayAdapter<Image> Adapter;
    GridView tableGrid;
    private Context context;
    private ImageView randomImage;

    private Player p;
    private Game g;


    private static List<Animator> animations = new ArrayList<Animator>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        p = new Player("aaorn",0);
        g = new Game(p);

        intent= new Intent(this, AudioIntentService.class);
        intent.putExtra("operacio", "inici");
        Log.d("SAVA", "Table Activity");
        startService(intent);

        context = getApplicationContext();
        tableGrid = (GridView) findViewById(R.id.table);

        randomImage = (ImageView) findViewById(R.id.randomImage);

        //changeImage();

        randomImage.setImageResource(g.changeImage());

        fadeImage(3000,1,false);

        tableGrid.setAdapter(new ImageAdapter(context,g.images));

        tableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                g.imagesSelected.add(position);

                if(g.checkResponse()) {
                    if(g.imagesSelected.size() == g.lastImages.size()) {
                        Log.d("SAVA", "OK");
                        g.setTurno(g.getTurno()+1);
                        g.getPlayer().setPoints(g.getTurno());
                        showResponses();
                    }
                }else {
                    Log.d("SAVA", "MAL");
                    goEndActivity();
                }
            }
        });

    }

    /*private void checkResponse() {
        boolean isValid = false;
        for (int i = 0; i < g.imagesSelected.size(); i++) {
            if(g.lastImages.get(i) == g.imagesSelected.get(i)) {
                isValid = true;
            } else {
                isValid = false;
            }

        }

        if(isValid) {
            if(g.imagesSelected.size() == g.lastImages.size()) {
                Log.d("SAVA", "OK");
                g.setTurno(g.getTurno()+1);
                g.getPlayer().setPoints(g.getTurno());
                showResponses();
            }
        }else {
            Log.d("SAVA", "MAL");
            goEndActivity();
        }

    }*/

    private void showResponses() {
        boolean isStop = false;

        Log.d("SAVA", "SHOW RESPONSES");
        Log.d("SAVA", g.imagesSelected.toString());
        AnimatorSet s = new AnimatorSet();
        animations.clear();

        for (int i = 0; i < g.imagesSelected.size(); i++) {
            final int finalI = i;

            ObjectAnimator anim =
                    ObjectAnimator.ofArgb(tableGrid.getChildAt(g.imagesSelected.get(i)),"BackgroundColor",Color.DKGRAY);
            anim.setDuration(1000);
            anim.setStartDelay(2000);

            anim.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animator) {
                    tableGrid.getChildAt(g.imagesSelected.get(finalI)).setBackgroundColor(Color.DKGRAY);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    tableGrid.getChildAt(g.imagesSelected.get(finalI)).setBackgroundColor(getResources().getColor(android.R.color.transparent));
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

        randomImage.setAlpha(0.0f);
        fadeImage(1000,1,true);
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
                                randomImage.setImageResource(g.changeImage());
                            }
                        }
                    });
        }
    }

    /*private void changeImage() {
        g.randomImageSelected = r.nextInt(images.length);
        Log.d("SAVA", "New image "+g.randomImageSelected);
        g.lastImages.add(g.randomImageSelected);
        randomImage.setImageResource(images[g.randomImageSelected]);
    }*/

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
                    intent.putExtra("operacio", "inici");
                    startService(intent);
                } else {
                    intent.putExtra("operacio", "pausa");
                    startService(intent);
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
        intent.putExtra(EXTRA_MISSATGE, "" );
        startActivity(intent);
    }
}
