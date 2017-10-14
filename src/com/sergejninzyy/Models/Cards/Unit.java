package com.sergejninzyy.Models.Cards;

import com.sergejninzyy.GameObject;
import com.sergejninzyy.Models.Field;
import javafx.util.Pair;

import java.util.ArrayList;

public class Unit {


    private int id;
    public Narod narod;
    public int hits;
    public boolean stan;
    public boolean animal;
    public Field who_i_stan;

    public Unit(Narod narod, GameObject gameObject) {
        this.narod = narod;
        switch (narod){
            case TAVR: hits = 3; break;
            case ITOSHIN: hits = 1; break;
            default: hits = 2; break;
        }
        id = gameObject.getUnit_id_counter();
    }

    public Unit copyUnit(GameObject gameObject)
    {
        Unit unit = new Unit(this.narod, gameObject);
//такой же id, что
        unit.setId(this.id);
        unit.setHits(this.hits);
        unit.animal = this.animal;
        unit.stan = this.stan;
        if (who_i_stan == null) unit.who_i_stan = null; else unit.who_i_stan = gameObject.FindField(this.who_i_stan);
        return unit;
    }


    public int getSteps()
    {
        switch (narod){
            case GUAVAR: return 2;
            default: return 1;
        }
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
        switch (narod)
        {
            case ITOSHIN: return 3;
            case CHEKATTA: return 0;
            case ULUTAU: return 0;
            default: return 1;
        }
    }

    public ArrayList<Ability> getAbilities() {
        ArrayList<Ability> result = new ArrayList<>();
        switch (narod){
            case ULUTAU: result.add(Ability.STAN); result.add(Ability.HEAL); break;
            case MECHNIC: result.add(Ability.FIRE_ARROW); break;
            case CHEKATTA: result.add(Ability.COMEBACK_CHEKATTA); break;
            case DJUNIT: result.add(Ability.ATTACK); result.add(Ability.DOUBLE_ATTACK); break;
            case VEDICH: result.add(Ability.TO_ANIMAL); break;
            //у итошина, тавра и гуавара нет других способностей, кроме атаки
            default: result.add(Ability.ATTACK); break;
        }
        return result;
    }

}
