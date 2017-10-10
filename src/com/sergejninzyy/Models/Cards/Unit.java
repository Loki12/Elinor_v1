package com.sergejninzyy.Models.Cards;

import java.util.ArrayList;

public class Unit {

    private int id;
    public Narod narod;
    private int hits;
    public boolean stan;
    public boolean animal;

    public Unit(Narod narod) {
        this.narod = narod;
    }

    public Unit copyUnit()
    {
        Unit unit = new Unit(this.narod);
        unit.setId(this.id);
        unit.setHits(this.hits);
        return unit;
    }

    public int getSteps()
    {
        if (this.narod == Narod.GUAVAR) return 2;
        else return 1;
    }

    public Narod getNarod() {
        return narod;
    }

    public void setNarod(Narod narod) {
        this.narod = narod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getAttack()
    {
        if (this.narod == Narod.CHEKATTA || this.narod == Narod.ULUTAU) return 0;
        if (this.narod == Narod.ITOSHIN) return 3;
        if (this.narod == Narod.DJUNIT || this.narod == Narod.MECHNIC || this.narod == Narod.GUAVAR || this.narod == Narod.VEDICH
                || this.narod == Narod.TAVR) return 1;
        else return -1;
    }

    public ArrayList<Ability> getAbilities() {
        ArrayList<Ability> result = new ArrayList<>();

        if (this.narod!=Narod.CHEKATTA && this.narod!=Narod.ULUTAU) result.add(Ability.ATTACK);
        if (this.narod==Narod.DJUNIT ) result.add(Ability.DOUBLE_ATTACK);
        if (this.narod!=Narod.CHEKATTA) result.add(Ability.COMEBACK_CHEKATTA);
        if (this.narod!=Narod.ULUTAU) { result.add(Ability.HEAL); result.add(Ability.STAN);}
        if (this.narod!=Narod.VEDICH) { result.add(Ability.TOANIMAL); result.add(Ability.TOVEDICH);}
        if (this.narod!=Narod.MECHNIC) result.add(Ability.FIRE_ARROW);

        return result;
    }
}
