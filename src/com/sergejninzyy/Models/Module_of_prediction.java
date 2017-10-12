package com.sergejninzyy.Models;

import com.sergejninzyy.GameObject;
import com.sergejninzyy.Models.Cards.Ability;
import javafx.util.Pair;

import java.util.ArrayList;

public class Module_of_prediction {


    public Module_of_prediction() {

        int rage = 1;
        int depth = 1;
    }


    public GameObject predict(GameObject gameObject, int rage, int depth) {

        //список возможных состояний после моего хода и их эффективность
        ArrayList<Pair<GameObject, Integer>> first_steps_effective = first_steps(gameObject, 0, gameObject.getPlayer(1));

        //!!!!!!!!!!!!!!!!!!!!!!!!!!
        //хочу увидеть список первых ходов с оценкой их эффективности

        ArrayList<Pair<GameObject, Integer>> res = new ArrayList<>();

        //для каждого состояния к которому мы можем прийти за наш ход, мы считаем эффективность на несколько ходов вперед
        for (Pair<GameObject, Integer> gameObjectIntegerPair : first_steps_effective) {
            Pair<GameObject, Integer> localpair = count_effective(gameObjectIntegerPair.getKey(), depth, gameObjectIntegerPair.getValue());
            res.add(localpair);
        }
        // итого, в переменной res мы получаем значение пар состояний к которым мы можем прийти за один ход и эффективность этих ходов на нужную нам глубину

        //здесь мы должны выбрать один единственный Gameobjectб который по сути и будет нашим ходом
        Pair<GameObject, Integer> max = new Pair<>(gameObject, 0);
        for (Pair<GameObject, Integer> pair : res) {
            if (max.getValue() < pair.getValue()) {
                max = pair;
            }
            System.out.println(pair.getValue());
        }

        return max.getKey();
    }


    //todo проверить логику происходящего
    private Pair<GameObject, Integer> count_effective(GameObject gameObject, int depth, Integer effectiv) {
        //сколько раз мы это повторяем
        //todo проверить деление по модулю
        ArrayList<Pair<GameObject, Integer>> first_steps_effective = first_steps(gameObject, effectiv, gameObject.getPlayer(depth % 2));

        if (depth == 1) return new Pair<>(gameObject, count(first_steps_effective));
        depth -= 1;
        //для каждого состояния к которому мы можем прийти за этот ход, мы считаем эффективность на несколько ходов вперед
        ArrayList<Pair<GameObject, Integer>> res = new ArrayList<>();
        for (Pair<GameObject, Integer> gameObjectIntegerPair : first_steps_effective) {
            Pair<GameObject, Integer> localpair = (count_effective(gameObjectIntegerPair.getKey(), depth, gameObjectIntegerPair.getValue()));
            res.add(localpair);
        }
        return new Pair<>(gameObject, count(res));
    }

    private Integer count(ArrayList<Pair<GameObject, Integer>> first_steps_effective) {

        Integer result = 0;
        for (Pair<GameObject, Integer> pair : first_steps_effective) {
            result += pair.getValue();
        }

        return result / first_steps_effective.size();

    }

    //нужно получить список всех возможносных состояний после моего хода и их оценку
    private ArrayList<Pair<GameObject, Integer>> first_steps(GameObject gameObject, Integer eff, Player player) {

        //берем список всех моих полей, ищем возможные ходы для каждого
        //для каждого возможного поля ищем варианты дейтвий
        //для каждого варианта действий создаем свой gameobject

        //список пар состояний после хода и поле, на которое мы сейчас походили
        ArrayList<Pair<GameObject, Field>> possible_steps = new ArrayList<>();
        for (Field f : player.getPlayersfields()) {
            possible_steps.addAll(possible_steps(f, gameObject));
        }

        //!!!!!!!!!
        //список первых ходов

        System.out.println("Список первых ходов");
        for (Pair<GameObject, Field> pair: possible_steps) {
            System.out.println(pair.getValue().getCoordinats());
        }


        //список состояний доcки после хода и действия
        ArrayList<Pair<GameObject, Integer>> possible_actions = new ArrayList<>();
        for (Pair<GameObject, Field> pair : possible_steps) {
            ArrayList<Ability> abilities = pair.getValue().getUnit().getAbilities();
            for (Ability ability : abilities) {
                ArrayList<Field> aimofability = pair.getValue().getAimofAbility(ability, pair.getValue().whosfield(gameObject), gameObject);
                for (Field field : aimofability) {
                    GameObject gameObjectClone = gameObject.gameObjectClone();
                    possible_actions.add(gameObjectClone.Action(gameObject, ability, pair.getValue(), field, eff));
                }
            }
        }

        return possible_actions;
    }

    private ArrayList<Pair<GameObject, Field>> possible_steps(Field old_field, GameObject gameObject) {

        ArrayList<Pair<GameObject, Field>> common_result = new ArrayList<>();
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
            gameObjectClone.ChangeFieldofcard(old_field, new_field);
            common_result.add(new Pair<>(gameObjectClone, new_field));
        }

        return common_result;
    }
}