package com.sergejninzyy.Models;

import com.sergejninzyy.GameObject;
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

    //todo решить эту огромную проблему - когда я копикую поля внутри player - я создаю копию и они уже не являются ссылками на те поля, что у нас есть в gameobject

    //нужно брать поле у настоящего player и искать такое же поле среди копий и помещать в массив к копии player
    //
    public Player copyPlayer(GameObject gameObjectClone)
    {
        Player player = new Player(this.number);
        for (Field f: this.getPlayersfields()) {
            player.getPlayersfields().add(gameObjectClone.FindField(f.getX(), f.getY(), f.getZ()));
        }

        /*for (Field real_field: this.getPlayersfields()) {
            player.getPlayersfields().add(real_field.copyField());
        }*/

        for (Unit real_unit: this.dead_units) {
            player.dead_units.add(real_unit.copyUnit());
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
