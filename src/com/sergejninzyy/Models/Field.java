package com.sergejninzyy.Models;

import com.sergejninzyy.Main;
import com.sergejninzyy.Models.Cards.Unit;

import java.util.ArrayList;

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
}
