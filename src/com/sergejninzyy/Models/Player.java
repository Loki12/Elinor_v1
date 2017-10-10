package com.sergejninzyy.Models;

import java.util.ArrayList;

public class Player {

    int number;
    private ArrayList<Field> playersfields;

    public Player(int i) {
        playersfields = new ArrayList<>();
        this.number = i;
    }

    public ArrayList<Field> getPlayersfields() {
        return playersfields;
    }

    public void setPlayersfields(ArrayList<Field> playersfields) {
        this.playersfields = playersfields;
    }

    private void addPlayerfield(Field field)
    {
        this.playersfields.add(field);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
