package com.sergejninzyy;

import com.sergejninzyy.Models.Cards.Ability;
import com.sergejninzyy.Models.Cards.Unit;
import com.sergejninzyy.Models.Field;
import com.sergejninzyy.Models.Intellect;
import com.sergejninzyy.Models.Player;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameObject {

    public ArrayList<Player> players;
    public ArrayList<Field> fields;
    public int lvl;

    public GameObject( int lvl){

        this.lvl = lvl;
        fields = new ArrayList<>();
        players = new ArrayList<>();

        try {
            FillFields(this.lvl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FillPlayers();
    }

    public GameObject gameObjectClone ()
    {
        GameObject gameObject = new GameObject(this.lvl);

        ArrayList<Player> new_players = new ArrayList<>();
        for (Player p: this.players) {
            new_players.add(p.copyPlayer());
        }
        return gameObject;
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

    public Integer Step(Field field_old, Field new_field, Field aim_of_ability, Ability ability, Integer eff)
    {
        ChangeFieldofcard(field_old, new_field);
        Pair<GameObject, Integer> pair = Action(ability, new_field, aim_of_ability, eff);
        return pair.getValue();
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


    //TODO check
    public int attack(Field f1, Field f2)
    {
            f2.getUnit().setHits(f2.getUnit().getHits() - f1.getUnit().getAttack());
            if (f2.getUnit().getHits() < 1) {
                f2.whosfield().dead_units.add(f2.getUnit());
                f2.setUnit(null);
                f2.whosfield().getPlayersfields().remove(f2);
                return 2;
            } else return 1;

    }

    //Todo доедлать остальные способности
    //возвращает состояние всего поля и оценку эффективности
    public Pair<GameObject, Integer> Action(Ability ability, Field my_unit_field, Field aim_of_ability, Integer eff) {
        Integer mark = eff;
        if (ability == Ability.ATTACK) {
           mark = this.attack(my_unit_field, aim_of_ability);
        }
        return new Pair<>(this, mark);
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //я даю поле откуда, поле куда походили, gameobject
    //задача ии - взять все возможные дейтсвия юнита, пройтись по всем, скопировать gameobject для каждого дейтсвия,
    // сделать ход и дейтвие, вернуть пару gameobject unteger

  /*  public Integer find_actions(Field old_field, Field new_field, Integer eff)
    {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(eff);
        Integer sum = eff;
        ArrayList<Ability> abilities = old_field.getUnit().getAbilities();
        for (Ability ab: abilities) {
            ArrayList<Field> arrayListaimofabilities = old_field.getAimofAbility(ab, old_field.whosfield());
            for (Field aimofability: arrayListaimofabilities) {
                Integer mark = gameObjectClone().Step(old_field, new_field, aimofability, ab, eff);
                integerArrayList.add(mark);
                sum+=mark;
            }
        }
        return sum/integerArrayList.size();
    }*/
}
