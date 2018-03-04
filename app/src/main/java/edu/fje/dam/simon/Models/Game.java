package edu.fje.dam.simon.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import edu.fje.dam.simon.R;


/**
 * Clase Game, contiene las propiedades y métodos
 * para gestionar una partida
 */
public class Game {
    // jugador
    private Player player;
    // figura random
    public int randomImageSelected;
    // lista con las ultimas figuras correctas
    public ArrayList<Integer> lastImages = new ArrayList<>();
    // lista con las figuras que el jugador a seleccionado
    public ArrayList<Integer> imagesSelected = new ArrayList<>();
    // turno de la partida
    private int turno = 0;
    // array con las figuras
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

    /**
     * Constructor
     * @param player instancia del jugador
     */
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
        this.player.setPoints(turno);
    }

    /**
     * Método que devuelve una nueva imagen cuando cambia
     * el turno. Esta imagen se añade a la lista lastImages
     * y dentro de la varible randomImageSelected.
     * @return int
     */
    public int changeImage() {
        Random r = new Random();
        randomImageSelected = r.nextInt(images.length);
        Log.d("SAVA", "New image "+randomImageSelected);
        lastImages.add(randomImageSelected);

        return images[randomImageSelected];
    }

    /**
     * Método que comprueba que las respuestas del usuario(imagesSelected)
     * coinciden con las imagenes generadas por la máquina (lastImages)
     * @return
     */
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
