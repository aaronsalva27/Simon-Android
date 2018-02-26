package edu.fje.dam.simon.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import edu.fje.dam.simon.R;

/**
 * Created by sava on 26/02/18.
 */

public class Game {
    private Player player;
    public int randomImageSelected;
    public ArrayList<Integer> lastImages = new ArrayList<>();
    public ArrayList<Integer> imagesSelected = new ArrayList<>();
    private int turno = 0;
    public int images[] = {
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

    public Game(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int changeImage() {
        Random r = new Random();
        randomImageSelected = r.nextInt(images.length);
        Log.d("SAVA", "New image "+randomImageSelected);
        lastImages.add(randomImageSelected);

        return images[randomImageSelected];
    }

    public boolean checkResponse() {
        boolean isValid = false;
        for (int i = 0; i < imagesSelected.size(); i++) {
            if (lastImages.get(i) == imagesSelected.get(i)) {
                isValid = true;
            } else {
                isValid = false;
            }
        }
        return isValid;
    }

}
