package edu.fje.dam.simon.FireBase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.fje.dam.simon.Models.Player;
import edu.fje.dam.simon.R;

/**
 * Created by arand on 03/03/2018.
 */

public class Llista_Players extends ArrayAdapter<Player> {
    private Activity context;
    List<Player> players;

    public Llista_Players(Activity context, List<Player> players) {
        super(context, R.layout.llista, players);

        this.context = context;
        this.players = players;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.llista, null, true);

        TextView nom = (TextView) listViewItem.findViewById(R.id.nom);
        TextView punts = (TextView) listViewItem.findViewById(R.id.punts);

        Player player = players.get(position);
        nom.setText(player.getName());
        punts.setText(String.valueOf(player.getPoints()));
;

        return listViewItem;
    }
}
