package com.sergejninzyy.Models;

import com.sergejninzyy.GameObject;
import com.sergejninzyy.Models.Cards.Ability;
import com.sergejninzyy.Models.Cards.Narod;
import com.sergejninzyy.Models.Cards.Unit;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;

public class Module_of_prediction {


    public Module_of_prediction() {

        int rage = 1;
        int depth = 1;
    }


    public GameObject predict(GameObject gameObject, int rage, int depth) {

        //список возможных состояний после моего хода и их эффективность

        ArrayList<Pair<GameObject, Integer>> first_steps_effective = first_steps(gameObject, 0, gameObject.getPlayer(1));

        //сделаем масштабную проверку

        /*for (Pair<GameObject, Integer> pair: first_steps_effective) {
            System.out.println("Состояние номер " + pair.getKey().toString() + "Эффективность этого состояния = " + pair.getValue());

            System.out.println("Игрок номер 0 и список его полей");
            for (Field field: pair.getKey().players.get(0).getPlayersfields()) {
                System.out.println(field.getCoordinats());
            }

            System.out.println("ИИ и список его полей");
            for (Field field: pair.getKey().players.get(1).getPlayersfields()) {
                System.out.println(field.getCoordinats());
            }
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }*/

        ArrayList<Pair<GameObject, Integer>> res = new ArrayList<>();

        //для каждого состояния к которому мы можем прийти за наш ход, мы считаем эффективность на несколько ходов вперед
        for (Pair<GameObject, Integer> gameObjectIntegerPair : first_steps_effective) {
            Pair<GameObject, Integer> local_pair = count_effective(gameObjectIntegerPair.getKey(), depth, gameObjectIntegerPair.getValue());
            res.add(local_pair);
        }
        // итого, в переменной res мы получаем значение пар состояний к которым мы можем прийти за один ход и эффективность этих ходов на нужную нам глубину
        return final_chose(res).getKey();
    }

    //todo проверить
    private Pair<GameObject, Integer> count_effective(GameObject gameObject, int depth, Integer effectiv) {

        ArrayList<Pair<GameObject, Integer>> first_steps_effective = first_steps(gameObject, effectiv, gameObject.getPlayer(depth % 2));

        if (depth == 1){
            return count(first_steps_effective, gameObject);
        }
        depth -= 1;
        //для каждого состояния к которому мы можем прийти за этот ход, мы считаем эффективность на несколько ходов вперед
        ArrayList<Pair<GameObject, Integer>> res = new ArrayList<>();
        for (Pair<GameObject, Integer> gameObjectIntegerPair : first_steps_effective) {
            Pair<GameObject, Integer> localpair = (count_effective(gameObjectIntegerPair.getKey(), depth, gameObjectIntegerPair.getValue()));
            res.add(localpair);
        }
        return null;
    }

    //todo перепродумать оценку эффективности
    public  static Pair<GameObject, Integer> count(ArrayList<Pair<GameObject, Integer>> first_steps_effective, GameObject gameObject) {

        Integer result = 0;
        for (Pair<GameObject, Integer> pair : first_steps_effective) {
            result += pair.getValue();
        }
        return new Pair<>(gameObject, result / first_steps_effective.size());

    }

    //перепродумать выбор хода
    private Pair<GameObject, Integer> final_chose(ArrayList<Pair<GameObject, Integer>> final_steps)
    {
        Pair<GameObject, Integer> gameObjectIntegerPair = null;
        if (final_steps.size()!=0) gameObjectIntegerPair = final_steps.get(0);
        //todo учесть вариант, когда у нас нет доступных ходов
        //todo учесть возможность наличия нескольких ходов с одинаковой эффективностью
        for (Pair<GameObject, Integer> pair: final_steps) {
            if (gameObjectIntegerPair.getValue()<pair.getValue())
            {
                gameObjectIntegerPair = pair;
            }
        }
        return gameObjectIntegerPair;
    }

    //нужно получить список всех возможносных состояний после моего хода и их оценку
    private ArrayList<Pair<GameObject, Integer>> first_steps(GameObject gameObject, Integer eff, Player player) {

        //берем список всех моих полей, ищем возможные ходы для каждого
        //для каждого возможного поля ищем варианты дейтвий
        //для каждого варианта действий создаем свой gameobject
        //список пар состояний после хода и поле, на которое мы сейчас походили
        ArrayList<Pair<Pair<GameObject, Field>, Field>> possible_steps = new ArrayList<>();

        for (Field f : player.getPlayersfields()) {
            possible_steps.addAll(possible_steps(f, gameObject));
        }

        //для чекатта
        //создаем копии текущего состояния с замененными чекаттами
       //todo здесь тоже все проверить
        for (Field field: player.getPlayersfields()) {
            if(field.getUnit().narod == Narod.CHEKATTA)
            {
                for (Unit unit: player.dead_units) {
                    possible_steps.addAll(create_copy_of_gameObject_for_chekatta(gameObject, field, unit));
                }
            }
        }

        //для каждого состояния - запускаем
        //список первых возможных ходов
        /*System.out.println("Список первых возможных ходов");
        for (Pair<Pair<GameObject, Field>, Field> pair: possible_steps) {
            System.out.println(pair.getKey().getValue().getCoordinats());
        }*/

        //список состояний доcки после хода и действия
        ArrayList<Pair<GameObject, Integer>> possible_actions = new ArrayList<>();

        //для каждого из возможных ходов
        for (Pair<Pair<GameObject, Field>, Field> pair : possible_steps) {
            //мы находим способности этого юнита, исходя из поля, на которое мы уже походили
            ArrayList<Ability> abilities = pair.getKey().getValue().getUnit().getAbilities();
            //для каждой способности
            for (Ability ability : abilities) {
                //мы находим цели этой способности, с учетом поля на которое мы уже походили
                ArrayList<Field> aimofability = pair.getKey().getValue().getAimofAbility(ability, pair.getValue().whosfield(gameObject), gameObject);

                //если нет целей для способности
                if (aimofability.size() == 0)
                {
                    possible_actions.add(new Pair<>(pair.getKey().getKey(), eff));
                }

                //для каждого поля, на которое у нас направлена способность
                for (Field field : aimofability) {
                    GameObject gameObjectClone = pair.getKey().getKey().gameObjectClone();
                    Field copy_new_field_aim_for_attack = gameObjectClone.FindField(field.getX(), field.getY(), field.getZ());
                    //получаем копию нашего поля в копии gameobject
                    Field curr = pair.getKey().getValue();
                    Field copy_old_field = gameObjectClone.FindField(curr.getX(), curr.getY(), curr.getZ());
                    possible_actions.add(gameObjectClone.Action(ability, copy_old_field, copy_new_field_aim_for_attack, eff));
                }
            }
        }

        return possible_actions;
    }


    //todo проверить, проверить и еще раз проверить
    private Collection<? extends Pair<Pair<GameObject, Field>, Field>> create_copy_of_gameObject_for_chekatta(GameObject gameObject, Field field, Unit unit) {
        GameObject gameObjectClone = gameObject.gameObjectClone();
        Field copy_old_field = gameObjectClone.FindField(field.getX(), field.getY(), field.getZ());
        //todo проверить этот момент
        copy_old_field.setUnit(gameObjectClone.FindUnit(unit.getId()));

        if (field.getUnit()!=null && copy_old_field.getUnit() == null) {
            System.out.println("Какой-то пипец");
        }
        return possible_steps(copy_old_field, gameObjectClone);
    }

    private ArrayList<Pair<Pair<GameObject, Field>, Field>> possible_steps(Field old_field, GameObject gameObject) {
        ArrayList<Pair<Pair<GameObject, Field>, Field>> common_result = new ArrayList<>();

        //если я застанен - не могу ходить
        if (old_field.getUnit().stan) return common_result;

        ArrayList<Field> result;


        //находим всех соседей данного поля
        if (old_field.getUnit().getSteps() == 2) {
            //TODO исправить ходы у гуавара
            result = old_field.GetNeighbours(2, gameObject);
            result.removeAll(old_field.GetNeighbours(1, gameObject));
        } else result = old_field.GetNeighbours(1, gameObject);

        //оставляем только незанятые клетки
        ArrayList<Field> free_fields = new ArrayList<>();
        for (Field field : result) {
            if (field.getUnit() == null) {
                free_fields.add(field);
            }
        }

        //создаем клона gameobject для каждого варианта хода, ходим на каждом клоне и записываем изменненые клоны поля и поля в массив
        for (Field new_field: free_fields) {
            GameObject gameObjectClone = gameObject.gameObjectClone();
            Field copy_old_field = gameObjectClone.FindField(old_field.getX(), old_field.getY(), old_field.getZ());
            Field copy_new_field = gameObjectClone.FindField(new_field.getX(), new_field.getY(), new_field.getZ());
            gameObjectClone.ChangeFieldofcard(copy_old_field, copy_new_field);
            Pair<GameObject, Field> curr_pair = new Pair<>(gameObjectClone, copy_new_field);

            common_result.add(new Pair<>(curr_pair, old_field));
        }

        return common_result;
    }
}