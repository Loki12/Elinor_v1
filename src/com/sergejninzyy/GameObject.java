package com.sergejninzyy;

import com.sergejninzyy.Models.*;
import com.sergejninzyy.Models.Cards.Ability;
import com.sergejninzyy.Models.Cards.Narod;
import com.sergejninzyy.Models.Cards.Unit;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameObject {

    public int unit_id_counter;
    public ArrayList<Player> players;
    public ArrayList<Field> fields;
    public int lvl;

    public GameObject( int lvl){

        this.lvl = lvl;
        fields = new ArrayList<>();
        players = new ArrayList<>();
        unit_id_counter = 0;

        try {
            FillFields(this.lvl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FillPlayers();
    }

    public int getUnit_id_counter() {
        return ++unit_id_counter;
    }

    public void setUnit_id_counter(int unit_id_counter) {
        this.unit_id_counter = unit_id_counter;
    }

    /*public GameObject gameObjectClone ()
    {
        GameObject gameObjectClone = new GameObject(this.lvl);

        gameObjectClone.fields.clear();
        for (Field real_field:this.fields) {
            gameObjectClone.fields.add(real_field.copyField(this));
        }

        gameObjectClone.players.clear();
        for (Player real_player:this.players) {
            gameObjectClone.players.add(real_player.copyPlayer(gameObjectClone));
        }
        return gameObjectClone;
    }*/

    public GameObject gameObjectClone()
    {
        GameObject gameObjectClone = new GameObject(this.lvl);
        gameObjectClone.setUnit_id_counter(this.unit_id_counter);
        gameObjectClone.fields.clear();
        for (Field real_field:this.fields) {
            gameObjectClone.fields.add(real_field.copyField_withoutUnit(this));
        }

        for (int i = 0; i < gameObjectClone.fields.size(); i++) {
            if (this.fields.get(i).getUnit() == null) { gameObjectClone.fields.get(i).setUnit(null); }
            else gameObjectClone.fields.get(i).setUnit(this.fields.get(i).getUnit().copyUnit(this));
        }

        gameObjectClone.players.clear();
        for (Player real_player:this.players) {
            gameObjectClone.players.add(real_player.copyPlayer(gameObjectClone));
        }
        return gameObjectClone;
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
        for (Field f: this.fields) {
            if (f.getX() == x && f.getY() == y && f.getZ() == z)
            {
                return f;
            }
        }
        return null;
    }

    public void ChangeFieldofcard(Field old_field, Field new_field)
    {
        //берешь юнит, ставишь на новое поле
        //удаляешь со старого поля юнит
        //удаляешь поле из пула игрока
        //добавляешь в пул игрока новое поле

        //если я улутау, то при ходе я автоматически оставялю без стана того, кого я станю
        if (old_field.getUnit().narod == Narod.ULUTAU && old_field.getUnit().who_i_stan!=null) {
            //переписать через set/get who i stan
            old_field.getUnit().who_i_stan.getUnit().stan = false;
            old_field.getUnit().who_i_stan = null;
        }
        //если я ведич-животное - автоматически превращаюсь в ведича
        if (old_field.getUnit().narod == Narod.VEDICH) old_field.getUnit().animal = false;

        new_field.setUnit(old_field.getUnit());
        old_field.setUnit(null);
        old_field.whosfield(this).getPlayersfields().add(new_field);
        old_field.whosfield(this).getPlayersfields().remove(old_field);
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

    //возвращает состояние всего поля и оценку эффективности
    public Pair<GameObject, Integer> Action(Ability ability, Field my_unit_field, Field aim_of_ability, Integer eff) {
        Integer mark = eff;
        if (my_unit_field.getUnit().stan)
        {
            System.out.println("Какого я ухзнаю о стане только на уровне Action");
        }
            switch (ability){
                case ATTACK: mark += this.attack(my_unit_field, aim_of_ability);
                case STAN: mark += this.stan(my_unit_field, aim_of_ability);
                case HEAL: mark += this.heal(my_unit_field, aim_of_ability);
                case DOUBLE_ATTACK: mark += this.double_attack(my_unit_field, aim_of_ability);
                case FIRE_ARROW: mark += this.attack(my_unit_field, aim_of_ability);
                //todo заменить на int на float
                case TO_ANIMAL: aim_of_ability.getUnit().animal=true; mark += 0;
                case TO_VEDICH: aim_of_ability.getUnit().animal=false; mark += 0;
                case COMEBACK_CHEKATTA: mark += this.come_back_chekatta(my_unit_field, aim_of_ability, eff);

            }
        return new Pair<>(this, mark);
    }

    private Integer come_back_chekatta(Field my_unit_field, Field aim_of_ability, Integer eff) {

        my_unit_field.setUnit(aim_of_ability.getUnit());
        ArrayList<Ability> abilities = my_unit_field.getUnit().getAbilities();
        //список состояний доcки после хода и действия
        ArrayList<Pair<GameObject, Integer>> possible_actions = new ArrayList<>();
            //для каждой способности
            for (Ability ability : abilities) {
                //мы находим цели этой способности, с учетом поля на которое мы уже походили
                ArrayList<Field> aimofability = aim_of_ability.getAimofAbility(ability, aim_of_ability.whosfield(this), this);
                //если нет целей для способности
                if (aimofability.size() == 0)
                {
                    possible_actions.add(new Pair<>(this, eff));
                }
                //для каждого поля, на которое у нас направлена способность
                for (Field field : aimofability) {
                    GameObject gameObjectClone = this.gameObjectClone();
                    Field copy_new_field_aim_for_attack = gameObjectClone.FindField(field.getX(), field.getY(), field.getZ());
                    //получаем копию нашего поля в копии gameobject
                    Field copy_old_field = gameObjectClone.FindField(aim_of_ability.getX(), aim_of_ability.getY(), aim_of_ability.getZ());
                    possible_actions.add(gameObjectClone.Action(ability, copy_old_field, copy_new_field_aim_for_attack, eff));
                }
            }

            return Module_of_prediction.count(possible_actions, this).getValue();
        }

    //todo непроверено
    private Integer double_attack(Field my_unit_field, Field aim_of_ability) {

        if (aim_of_ability instanceof DoubleField){
         return attack(my_unit_field, ((DoubleField) aim_of_ability).field_field) +
            attack(my_unit_field, ((DoubleField) aim_of_ability).second_Field);
        }
        else return attack(my_unit_field, aim_of_ability);
    }

    //todo непроверено
    private Integer heal(Field my_unit_field, Field aim_of_ability) {
        aim_of_ability.getUnit().hits++;
        if (aim_of_ability.whosfield(this) == players.get(0)) return -1;
        else return 1;
    }

    //todo переписать, чтобы учитывать кого застанил
    private Integer stan(Field my_unit_field, Field aim_of_ability) {
        //отмечаем у себя кого я станю
        my_unit_field.getUnit().who_i_stan = aim_of_ability;
        //отмечают у того, кого станю, он застанен
        aim_of_ability.getUnit().stan = true;
        if (my_unit_field.whosfield(this) == players.get(0)) return -1;
        //if (my_unit_field.whosfield(this) == players.get(1)) return 1;
        else return 1;
    }

    //TODO переписать, чтобы можно было нормально вызвать и для ИИ и для других
    //возвращаем эффективность для ИИ
    public int attack(Field f1, Field f2)
    {
        Integer current_mark;
        //если поле которое направлена атака придалжежит ИИ
        if (f2.whosfield(this) == players.get(1)) current_mark = -1;
            //если поле не принадлежит ИИ
        else current_mark = 1;
        f2.getUnit().setHits(f2.getUnit().getHits() - f1.getUnit().getAttack());
        if (f2.getUnit().getHits() < 1) {
            f2.whosfield(this).dead_units.add(f2.getUnit());
            f2.setUnit(null);
            f2.whosfield(this).getPlayersfields().remove(f2);
            current_mark*=2;
        }
        return current_mark;
    }

    public Unit FindUnit(int id) {

        for (Field field: this.fields) {
            if (field.getUnit().getId() == id)
            {
                return field.getUnit();
            }
        }
        return null;
    }

    public Field FindField(Field real_field) {
        return this.FindField(real_field.getX(), real_field.getY(), real_field.getZ());
    }
}
