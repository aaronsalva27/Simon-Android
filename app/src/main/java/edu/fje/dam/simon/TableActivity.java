package edu.fje.dam.simon;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

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
    public Context context;

    int images[] = {
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

        tableGrid.setAdapter(new ImageAdapter(context,images));
        //figuresGrid.setAdapter(Adapter);

        tableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                Toast.makeText( context,"pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
            }
        });
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
