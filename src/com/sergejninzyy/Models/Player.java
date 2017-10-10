package com.sergejninzyy.Models;

import com.sergejninzyy.Models.Cards.Unit;

import java.util.ArrayList;

public class Player {

    int number;
    private ArrayList<Field> playersfields;
    public ArrayList<Unit> dead_units = new ArrayList<>();

    public Player(int i) {
        playersfields = new ArrayList<>();
        this.number = i;
    }

    public Player copyPlayer()
    {
        Player player = new Player(this.number);
        ArrayList<Field> fieldArrayList = new ArrayList<>();
        for (Field f: this.getPlayersfields()) {
            fieldArrayList.add(f.copyField());
        }

        ArrayList<Unit> unitsArrayList = new ArrayList<>();
        for (Unit u: this.dead_units) {
            unitsArrayList.add(u.copyUnit());
        }
        return player;
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
