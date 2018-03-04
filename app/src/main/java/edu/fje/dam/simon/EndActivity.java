package edu.fje.dam.simon;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;

import android.text.TextUtils;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.fje.dam.simon.Models.Player;
import edu.fje.dam.simon.Services.FireBaseIntentService;

/**
 * Actividad que sale al final de una partida con tu puntuación.
 */
public class EndActivity extends AppCompatActivity {
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";

    // boton para volver al inicio
    protected Button buttonInici;
    // textView con los segundos que el jugador a tardado
    protected TextView textViewPunt;

    private String nomPunts;
    private String Nom;
    private String Punts;
    private String[] arrayMissatge;

    List<Player> players;
    DatabaseReference Simon;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Simon = FirebaseDatabase.getInstance().getReference();

        // recuperamos información de la activity anterior
        Intent intent = getIntent();
        String missatge = intent.getStringExtra(TableActivity.EXTRA_MISSATGE);

        nomPunts = missatge;

        //SEPARAMOS EL NOMBRE Y LOS PUNTOS PARA PONERLOS EN LA BBDD
        arrayMissatge = nomPunts.split(", ");
        Nom = arrayMissatge[0];
        Punts=arrayMissatge[1];
        Toast.makeText(this, Nom, Toast.LENGTH_LONG).show();
        Toast.makeText(this, Punts, Toast.LENGTH_LONG).show();

        textViewPunt = (TextView) findViewById(R.id.textViewPunt);
        textViewPunt.setText(missatge + " Punts");

        VistaCanvas vistaPropia = (VistaCanvas) findViewById(R.id.vista);
        vistaPropia.setX(200);
        vistaPropia.setY(100);
        vistaPropia.setColor(Color.RED);
        vistaPropia.setCadena("  PUNTUACIÓ");


        players = new ArrayList<>();
        //AFEGIM EL JUGADOR I LA PUNTUACIÓ AL FIREBASE
        Intent intentService= new Intent(this, FireBaseIntentService.class);
        intentService.putExtra("nom", Nom);
        intentService.putExtra("punts", Punts);
        startService(intentService);
        //afegirPlayer();

        buttonInici = (Button) findViewById(R.id.buttonInici);
        // función anonima para volver al inicio
        buttonInici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Go to: WelcomeActivity");
                toWelcomeActivity();
            }
        });
    }

    //Mètode per anar al Main Activity
    public void toWelcomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MISSATGE, nomPunts);
        startActivity(intent);
    }


    private void afegirPlayer() {
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
