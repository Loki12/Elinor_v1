package com.sergejninzyy.Models;
import com.sergejninzyy.GameObject;
import com.sergejninzyy.Models.Cards.Narod;
import com.sergejninzyy.Models.Cards.Unit;

import java.util.*;

public class Intellect extends Player {

    private static final int MAXINE = 32000;

    public Intellect(int i) {
        super(i);
    }


    public Field AlignmentSet (GameObject gameObject, Unit unit, int num_of_step)
    {

        if (num_of_step == 0) {
            //возвращаем шестиугольник на противоположной стороне
            Field first_step = gameObject.getPlayer(0).getPlayersfields().get(0);
            return gameObject.FindField(-first_step.getX(), -first_step.getY(), -first_step.getZ());
            }
            else {
                if (num_of_step == 20)
                {
                    //что делать, если это последний ход
                }
                else {

                    // итого у нас те клетки, на которые мы можем ходить
                List<Field> list1 = FreeOurFields(gameObject);
                //для каждой этой клетки нужно посчитать расстояние до ближайшей клетки противника
                SortedMap<Integer, Field> listmap = CountDistance(list1, gameObject);

                    if (unit.narod == Narod.CHEKATTA|| unit.narod==Narod.ULUTAU) {
                        //если чекатта или улутау - ставим на максимально возможном расстоянии
                        //находим самую дальнюю
                        // !!!!переписать в функциональном стиле //найти учебники по Java //найти учебники по Kotlin
                        int max = 0;
                        Map.Entry<Integer, Field> entry = null;
                        for(Map.Entry<Integer, Field> x: listmap.entrySet()) {
                            if (max<x.getKey()) {
                                entry = x;
                            }
                        }
                        return entry.getValue();
                    }

                    if (unit.narod == Narod.MECHNIC|| unit.narod==Narod.VEDICH) {
                        //если механик или ведич
                        //не ближе, чем на расстоянии 3, но и не на максимальном
                        //Stream stream = listmap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue));

                        Iterator<Map.Entry<Integer,Field>> iterator = listmap.entrySet().iterator();
                        Map.Entry<Integer, Field> curr = iterator.next();

                        while (iterator.hasNext())
                        {
                            Map.Entry<Integer, Field> dist = iterator.next();
                                if (curr.getKey()<3 && dist.getKey()>curr.getKey()) {
                                    curr = dist;
                                    break;
                                }
                                if (curr.getKey()>=3 && dist.getKey()>curr.getKey() && iterator.hasNext()) {
                                    curr = dist;
                                }
                        }
                        return curr.getValue();
                    }

                    else
                    {
                        //если все остальные
                        // как можно ближе, но не ближе 3. Или вплотную.
                        Iterator<Map.Entry<Integer, Field>> iterator = listmap.entrySet().iterator();
                        Map.Entry<Integer, Field> curr = iterator.next();
                        return curr.getValue();
                    }
                }

            }

        return null;
    }

    private SortedMap CountDistance(List<Field> list, GameObject gameObject) {
        SortedMap<Integer, Field>result = new TreeMap<>();
        for (Field f: list) {
            int min_distance = MAXINE;
            for (Field f2: gameObject.getPlayer(0).getPlayersfields()) {
                int curr_dis = gameObject.DistanceBetween(f, f2);
                if (min_distance>curr_dis)
                {
                    min_distance = curr_dis;
                }
            }
            result.put(min_distance, f);
        }
        return result;
    }

    //ищем клетки, на которые можем поставить следующую карту

    public List<Field> FreeOurFields(GameObject gameObject)
    {
        ////что делать, если это не первый и не последний ход
            //вычисляем на какие мы можем ходит
                //у всех своих берем соседние
                //убираем свои поля и повторяющиеся
                //отбрасываем занятые
        //берем соседние
        ArrayList<Field> result = new ArrayList<>();
        for (Field f: this.getPlayersfields()) {
            result.addAll(f.GetNeighbours(1, gameObject));
        }
        //убираем свои поля
        result.removeAll(this.getPlayersfields());
        //убираем все повторячющиеся
//!!!!!проверить
        HashSet<Field> sort_arr = new HashSet<>(result);

        //отбрасываем занятые
        for (Field f: gameObject.getPlayer(0).getPlayersfields())
        {
            if (sort_arr.contains(f))
            {
                sort_arr.remove(f);
            }
        }

        List<Field> list = new ArrayList<>(sort_arr);
        return list;
    }


   /* public List<Field> FreeOurFields()
    {

        //что делать, если это не первый и не последний ход
            //вычисляем на какие мы можем ходит
            //у всех своих берем соседние
            //убираем свои поля и повторяющиеся
            //отбрасываем занятые

        ArrayList<Field> curr = new ArrayList<>();

        //у всех своих берем соседние
        for (Field f: this.getPlayersfields()) {
            curr.addAll(f.getNeighborfield());
        }
        //убираем все свои поля
        curr.removeAll(this.getPlayersfields());
        //убираем все повторячющиеся
        HashSet<Field> sort_arr = new HashSet<>(curr);

        //отбрасываем занятые
        for (Field f: sort_arr) {
            if (gameObject.getPlayer(0).getPlayersfields().contains(f))
            {
                sort_arr.remove(f);
            }
        }

        List<Field> list = new ArrayList<>(sort_arr);
        return list;
    }*/

}
