package edu.fje.dam.simon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.fje.dam.simon.SimonView.SimonActivity;

public class WelcomeActivity extends AppCompatActivity {
    //button para iniciar partida
    protected Button PlayButton;
    // nombre del jugador
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    protected EditText editTextNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        PlayButton = (Button) findViewById(R.id.ButtonPlay);

        // función anonima para cambiar de actividad
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Go to: SimonActivity");
                sendMessage();
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * realizamos un intent y passamos el nombre del jugador
     */
    public void sendMessage(){
        Intent intent = new Intent(this, TableActivity.class);
        //intent.putExtra(EXTRA_MISSATGE, editTextNom.getText().toString());
        startActivity(intent);
    }
}