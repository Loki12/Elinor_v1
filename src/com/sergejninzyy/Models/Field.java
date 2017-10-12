package com.sergejninzyy.Models;

import com.sergejninzyy.Main;
import com.sergejninzyy.Models.Cards.Ability;
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

    public Player whosfield()
    {
        for (Player p: Main.gameObject.players) {
            if (p.getPlayersfields().contains(this)) return p;
        }
        return null;
    }

    public Field copyField()
    {
        Unit unit = null;
        if (this.unit !=null)
        {
            unit = this.unit.copyUnit();
        }
        return new Field(this.x, this.y, this.z, unit);
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public ArrayList<Field> GetNeighbours(int N)
    {
        ArrayList<Field> result = new ArrayList<>();

        for (int i=-N; i<=N; i++)
        {
            int max = -N>-i-N ? -N:-i-N;
            int min = N < -i+N ? N: -i+N;
            for (int j = max; j<=min; j++)
            {
                z=-i-j;
                Field field = Main.gameObject.FindField(i, j, z);
                if(field!=null && field!=this)
                {
                    System.out.println(i + " " + j + " " + z);
                    result.add(field);
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
    public ArrayList<Field> getAimofAbility(Ability ability, Player player) {
        ArrayList<Field> result = new ArrayList<>();
        if (ability == Ability.TOVEDICH|| ability == Ability.TOANIMAL) result.add(this);
        if (ability == Ability.ATTACK||ability == Ability.STAN) result.addAll(this.find_aims_to_attack(player));

        return result;
    }

    private Collection<? extends Field> find_aims_to_attack(Player player) {
        ArrayList<Field> result = new ArrayList<>();
        ArrayList<Field> curr = new ArrayList<>();
        curr.addAll(this.GetNeighbours(1));
        for (Field f: curr) {
            if(f.getUnit()==null || f.whosfield() == player || f.getUnit().animal) {
                //do nothing
            }
            else result.add(f);
        }
        return result;
    }
}
