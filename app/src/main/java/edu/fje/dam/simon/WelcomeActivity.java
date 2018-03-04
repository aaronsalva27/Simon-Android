package edu.fje.dam.simon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.fje.dam.simon.FireBase.Llista_Players;
import edu.fje.dam.simon.Fragments.Main_Fragment;
import edu.fje.dam.simon.Models.Player;
import edu.fje.dam.simon.SimonView.SimonActivity;

public class WelcomeActivity extends AppCompatActivity {
    //button para iniciar partida
    protected Button PlayButton;
    // nombre del jugador
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    protected EditText editTextNom;

    DatabaseReference DBPlayers;
    List<Player> players;
    ListView llistaPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        editTextNom = (EditText) findViewById(R.id.editTextNom);
        PlayButton = (Button) findViewById(R.id.ButtonPlay);
        llistaPlayers = (ListView) findViewById(R.id.ListViewPuntuacions);

        players = new ArrayList<>();
        DBPlayers = FirebaseDatabase.getInstance().getReference();

        // función anonima para cambiar de actividad
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Go to: SimonActivity");
                sendMessage();
            }
        });

        // recuperamos información de la activity anterior
        //Intent intent = getIntent();
        //String missatge = intent.getStringExtra(EndActivity.EXTRA_MISSATGE);

        //missatge = nomPunts;

        //Simon = FirebaseDatabase.getInstance().getReference("players");
        //Toast.makeText(this, nomPunts, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBPlayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                players.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Player artista = postSnapshot.getValue(Player.class);
                    players.add(artista);
                }



                Collections.sort(players, new Comparator<Player>() {
                    @Override
                    public int compare(Player player, Player t1) {
                        // avoiding NullPointerException in case name is null
                        Integer v1 = new Integer(player.getPoints());
                        Integer v2 = new Integer(t1.getPoints());
                        return v2.compareTo(v1);

                    }
                });

                if(players.size() > 10) {
                    players = players.subList(0,9);
                }

                Llista_Players artistaAdapter = new Llista_Players(WelcomeActivity.this, players);
                llistaPlayers.setAdapter(artistaAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
    public void sendMessage(){
        Intent intent = new Intent(this, TableActivity.class);
        intent.putExtra(EXTRA_MISSATGE, editTextNom.getText().toString());
        Toast.makeText(this, editTextNom.getText(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}

