package com.sergejninzyy.Models.Cards;

import java.util.ArrayList;

public class Unit {

    private int id;
    public Narod narod;
    private int attack;
    private int hits;
    private ArrayList<Ability> abilities;

    public Unit(Narod narod) {
        this.narod = narod;
    }

    public Narod getNarod() {
        return narod;
    }

    public void setNarod(Narod narod) {
        this.narod = narod;
    }

}
