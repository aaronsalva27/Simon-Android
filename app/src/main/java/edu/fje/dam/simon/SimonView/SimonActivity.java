package edu.fje.dam.simon.SimonView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import edu.fje.dam.simon.EndActivity;
import edu.fje.dam.simon.R;
import edu.fje.dam.simon.Services.AudioIntentService;
import edu.fje.dam.simon.SimonView.Communicator;
import edu.fje.dam.simon.SimonView.SimonRandomFigureFragment;
import edu.fje.dam.simon.SimonView.SimonTableFragment;

public class SimonActivity extends AppCompatActivity implements Communicator {

    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    SimonRandomFigureFragment randomFigure;
    SimonTableFragment table;
    private Intent intent;
    private boolean isReproduint= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon);

        randomFigure = (SimonRandomFigureFragment) getFragmentManager().findFragmentById(R.id.fragmentRandom);
        table = (SimonTableFragment) getFragmentManager().findFragmentById(R.id.fragmentTable);

        this.mainToRandom("puta");

        intent= new Intent(this, AudioIntentService.class);
        intent.putExtra("operacio", "inici");
        Log.d("SAVA", "Simon Activity");
        startService(intent);

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

    @Override
    public void mainToRandom(String text) {
        randomFigure.newFigure(text);
    }

    @Override
    public void randomToMain(String text) {
        Log.d("INFO", "randomToMain: ."+text);
    }
}
