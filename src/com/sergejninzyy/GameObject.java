package com.sergejninzyy;

import com.sergejninzyy.Models.Cards.Unit;
import com.sergejninzyy.Models.Field;
import com.sergejninzyy.Models.Intellect;
import com.sergejninzyy.Models.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameObject {

    private ArrayList<Player> players;
    private ArrayList<Field> fields;

    public GameObject(){
        fields = new ArrayList<>();
        players = new ArrayList<>();

        try {
            FillFields(2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FillPlayers();
    }

    private void FillPlayers() {
        Player player0 = new Player(0);
        Intellect player1 = new Intellect(1);

        players.add(0, player0);
        players.add(1, player1);
    }

    private void FillFields(int lvl) throws FileNotFoundException {

        String test  = "Num = " + lvl;

        Scanner scanner = new Scanner(new File("C:\\Users\\Sergej\\Desktop\\1.txt"));
        String str = scanner.nextLine();
        if (str.equals(test)) {
            for (int i = 0; i < 19; i++) {
                Field field = new Field();
                field.setX(scanner.nextInt());
                field.setY(scanner.nextInt());
                field.setZ(scanner.nextInt());
                fields.add(field);
            }
        }
    }


    public void addField(Field field) {
        this.fields.add(field);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void addPlayer(Player player) {
        this.players.add(player.getNumber(), player);
    }


    public void AddCardtoPlayeronFiels(int playerNum, Unit unit, Field field)
    {
        field.setUnit(unit);
        players.get(playerNum).getPlayersfields().add(field);
    }

    public Field FindField (int x, int y, int z)
    {
        for (Field f: fields) {
            if (f.getX() == x && f.getY() == y && f.getZ() == z)
            {
                return f;
            }
        }
        return null;
    }

    public void ChangeFieldofcard(Field old_field, Field new_field)
    {
        new_field.setUnit(old_field.getUnit());
        old_field.setUnit(null);
    }

    public void ChangeFieldofcard(Unit unit, Field new_field)
    {
        Field field_old = null;
        for (Field field: fields
             ) {
            if (field.getUnit().equals(unit))
            {
                field_old = field;
                break;
            }
        }
        new_field.setUnit(unit);
        field_old.setUnit(null);
    }

    public int DistanceBetween(Field field1, Field field2)
    {
        return Math.abs(field1.getX()-field2.getX()) + Math.abs(field1.getY()-field2.getY()) + Math.abs(field1.getZ()-field2.getZ())/2;

    }

}
