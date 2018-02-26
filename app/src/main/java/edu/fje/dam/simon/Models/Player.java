package edu.fje.dam.simon.Models;

/**
 * Created by sava on 26/02/18.
 */

public class Player {
    private String Name;
    private int Points;

    public Player(String name, int points) {
        Name = name;
        Points = points;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }
}
