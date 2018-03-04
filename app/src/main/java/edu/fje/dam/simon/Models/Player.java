package edu.fje.dam.simon.Models;

/**
 * Clase Playerm contiene las propiedades de un jugador.
 */
public class Player {
    /**
     * nombre del jugador
     */
    private String Name;
    /**
     * puntos del jugador
     */
    private int Points;

    public Player() {

    }

    /**
     * Constuctor
     * @param name nombre del jugador
     * @param points puntos del jugador
     */
    public Player(String name, int points) {
        Name = name;
        Points = points;
    }

    /**
     * método accedor de lectura
     * @return devuelve el nombre del jugador
     */
    public String getName() {
        return Name;
    }

    /**
     * método accesor de escritura
     * @param name nombre del jugador
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * método accesor dec lectura
     * @return delvuelve los puntos del jugador
     */
    public int getPoints() {
        return Points;
    }

    /**
     * método accesor de lectura
     * @param points los puntos del jugador
     */
    public void setPoints(int points) {
        Points = points;
    }
}
