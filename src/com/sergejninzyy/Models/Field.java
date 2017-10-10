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
        Unit unit = this.unit.copyUnit();
        Field field = new Field(this.x, this.y, this.z, unit);
        return  field;
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
      /*for (int dx=-N; dx<=N; dx++)
        {
            int max = -N>=-dx-N? -N :-dx-N;
            int min = N>=-dx+N? N :-dx+N;
            for (int dy = max; dy<=min; dy++)
            {
                int dz = -dx-dy;
                result.add(Main.gameObject.FindField(this.getX()+dx, this.getY()+dy, this.getZ()+dz));
            }
        }*/
        for (int i = -N; i <=N ; i++) {
            for (int j = -N; j <=N ; j++) {
                for (int k = -N; k <=N; k++) {
                    int newx = this.x+i;
                    int newy = this.y+j;
                    int newz = this.z+k;
                    if(newx + newy + newz ==0)
                    {
                        Field field = Main.gameObject.FindField(newx, newy, newz);
                        if(field!=null && field!=this)
                        {
                            result.add(field);
                        }
                    }
                }
            }
        }
        //сделать нормально
        
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

        result.addAll(this.GetNeighbours(1));
        for (Field f: result) {
            if(f.getUnit()==null || f.whosfield() == player || f.getUnit().animal) result.remove(f);
        }
        return result;
    }
}
