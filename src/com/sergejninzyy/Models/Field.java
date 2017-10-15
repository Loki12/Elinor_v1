package com.sergejninzyy.Models;

import com.sergejninzyy.GameObject;
import com.sergejninzyy.Models.Cards.Ability;
import com.sergejninzyy.Models.Cards.Narod;
import com.sergejninzyy.Models.Cards.Unit;

import java.util.ArrayList;
import java.util.Collection;

public class Field {

    private int x;
    private int y;
    private int z;
    private Unit unit;

    public Field() {
    }

    public Field(int x, int y, int z, Unit unit) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.unit = unit;
    }

    public Field(Unit unit)
    {
        this.unit = unit;
        this.x = -10;
        this.y = -10;
        this.z = -10;
    }

    public Field(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        unit = null;
    }

    public Player whosfield(GameObject gameObject)
    {
        for (Player p: gameObject.players) {
            if (p.getPlayersfields().contains(this)) return p;
        }
        return null;
    }

    public Field copyField(GameObject gameObject)
    {
        Unit unit = null;
        if (this.unit !=null)
        {
            unit = this.unit.copyUnit(gameObject);
        }
        return new Field(this.x, this.y, this.z, unit);
    }

    public Field copyField_withoutUnit(GameObject gameObject)
    {
        return new Field(this.x, this.y, this.z);
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }


    //todo refactor
    public ArrayList<Field> GetNeighbours(int N, GameObject gameObject)
    {
        ArrayList<Field> result = new ArrayList<>();

        if (N==1){
            Field field = gameObject.FindField(x-1, y, z+1); if (field!=null) result.add(field);
            field = gameObject.FindField(x-1, y+1, z); if (field!=null) result.add(field);
            field = gameObject.FindField(x, y-1, z+1); if (field!=null) result.add(field);
            field = gameObject.FindField(x, y+1, z-1); if (field!=null) result.add(field);
            field = gameObject.FindField(x+1, y-1, z); if (field!=null) result.add(field);
            field = gameObject.FindField(x+1, y, z-1); if (field!=null) result.add(field);
        }
        else{
            for (int i=x-N; i>=x+N; i++){
                for (int j=y-N; j>=y+N; j++){
                    for (int k=z-N; k>=z+N; k++){
                        if (i+j+k==0)
                        {
                            Field field = gameObject.FindField(i, j, k); if (field!=null) result.add(field);
                        }
                    }
                }
            }
        }
        return result;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getCoordinats()
    {
        return x + " " + y + " " + z;
    }


    //TODO дописать остальные способоности
    public ArrayList<Field> getAimofAbility(Ability ability, Player player, GameObject gameObject) {
        ArrayList<Field> result = new ArrayList<>();
        if (this.unit.stan) return result;
        switch (ability){
            case TO_ANIMAL: result.add(this); break;
            case ATTACK: result.addAll(this.find_aims_to_attack_stan(player, gameObject, 1)); break;
            case STAN: result.addAll(this.find_aims_to_attack_stan(player, gameObject, 1)); break;
            case HEAL: result.addAll(this.find_aims_to_heal(player, gameObject)); break;
            case FIRE_ARROW: result.addAll(this.find_aims_to_attack_stan(player, gameObject, 2)); break;
            case DOUBLE_ATTACK: result.addAll(this.find_aims_for_double_attack(player, gameObject)); break;
            //todo переписать с учетом того, что мы в файле Field
            case COMEBACK_CHEKATTA: result.addAll(this.find_aims_of_come_back(player, gameObject)); break;

        }
        return result;
    }


    //todo проверить
    private Collection<? extends Field> find_aims_for_double_attack(Player player, GameObject gameObject) {
        ArrayList<Field> result = new ArrayList<>();

        ArrayList<Field> curr_result;
        //сначала добавим варианты для одной атаки
        curr_result = (ArrayList<Field>) this.find_aims_to_attack_stan(player, gameObject, 1);

        if (curr_result.size()==1) result.addAll(curr_result);
        else
        {
            result.addAll(recursive_findind_of_double_attack_aims_from_array_of_one_aims(curr_result, curr_result.size()));
        }

        return result;
    }

    private Collection<? extends Field> recursive_findind_of_double_attack_aims_from_array_of_one_aims(ArrayList<Field> curr_result, Integer size) {
        ArrayList<DoubleField> doubleFields = new ArrayList<>();
        for (int i = size; i!=1; i--)
        {
            for (int j= i-1; j!=0; j--)
            {
                doubleFields.add(new DoubleField(curr_result.get(i), curr_result.get(j)));
            }
        }
        return doubleFields;
    }

    //todo проверить
    private Collection<? extends Field> find_aims_of_come_back(Player player, GameObject gameObject) {
        ArrayList<Field> result = new ArrayList<>();
        for (Unit unit: player.dead_units) {
            result.add(new Field(unit));
        }
        return result;
    }


    //todo проверить
    private Collection<? extends Field> find_aims_to_attack_stan(Player player, GameObject gameObject, int N) {
        ArrayList<Field> result = new ArrayList<>();
        for (Field f: this.GetNeighbours(N, gameObject)) {
            if (f.getUnit()!=null && f.whosfield(gameObject)!=player &&
                    !f.getUnit().animal && !(f.unit.narod == Narod.GUAVAR && this.unit.narod == Narod.GUAVAR)) result.add(f);
        }
        return result;
    }

    private Collection<? extends Field> find_aims_to_heal(Player player, GameObject gameObject)
    {
        ArrayList<Field> result = new ArrayList<>();
        for (Field field: this.GetNeighbours(1, gameObject))
        {
            if (player.getPlayersfields().contains(field))
            {
                result.add(field);
            }
        }
        return result;
    }
}
