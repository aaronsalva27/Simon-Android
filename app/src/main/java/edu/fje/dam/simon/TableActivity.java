package edu.fje.dam.simon;

import android.content.Context;
import android.content.Intent;
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
import java.util.Random;

import edu.fje.dam.simon.Adapters.ImageAdapter;
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
    Random r = new Random();


    private int randomImageSelected;
    private ArrayList<Integer> lastImages = new ArrayList<>();
    private ArrayList<Integer> imagesSelected = new ArrayList<>();
    private int turno = 0;

    private int images[] = {
            R.drawable.quadrado_red,
            R.drawable.quadrado_blue,
            R.drawable.quadrado_green,
            R.drawable.quadrado_yellow,

            R.drawable.redonda_red,
            R.drawable.redonda_blue,
            R.drawable.redonda_green,
            R.drawable.redonda_yellow,

            R.drawable.rombo_red,
            R.drawable.rombo_blue,
            R.drawable.rombo_green,
            R.drawable.rombo_yellow
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        intent= new Intent(this, AudioIntentService.class);
        intent.putExtra("operacio", "inici");
        Log.d("SAVA", "Table Activity");
        startService(intent);

        context = getApplicationContext();
        tableGrid = (GridView) findViewById(R.id.table);


        randomImage = (ImageView) findViewById(R.id.randomImage);


        changeImage();

        fadeImage(3000,1,false);

        tableGrid.setAdapter(new ImageAdapter(context,images));

        tableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                Toast.makeText( context,"pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();

                imagesSelected.add(position);
                checkResponse();


            }
        });

    }

    private void checkResponse() {

        if(imagesSelected.get(turno) == lastImages.get(turno)) {
            Log.d("SAVA", "OK");
            turno++;
            showResponses();
        }else {
            Log.d("SAVA", "MAL");
        }

    }

    private void showResponses() {
        Log.d("SAVA", "SHOW RESPONSES");
        Log.d("SAVA", imagesSelected.toString());
        Log.d("SAVA", ""+imagesSelected.size());
        for (int i =0; i < imagesSelected.size(); i++) {
            final int finalI = i;
            Log.d("SAVA", ""+imagesSelected.size());
            randomImage.animate().setDuration(2000)
                    .withStartAction(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("SAVA", "SHOW RESPONSES"+finalI);
                            randomImage.setImageResource(images[finalI]);
                        }
                    });

        }

        randomImage.setAlpha(0.0f);
        fadeImage(3000,1,true);
    }

    private void fadeImage(int duration, int alpha, final boolean callback) {
        randomImage.animate().setDuration(duration).alpha(alpha)
            .withStartAction(new Runnable() {
                @Override
                public void run() {
                    if(callback) {
                        changeImage();
                    }
                }
            })

            .withEndAction(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void changeImage() {
        randomImageSelected = r.nextInt(images.length);
        Log.d("SAVA", "New image "+randomImageSelected);
        randomImage.setImageResource(images[randomImageSelected]);

        lastImages.add(randomImageSelected);

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
