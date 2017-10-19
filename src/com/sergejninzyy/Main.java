package com.sergejninzyy;

import com.sergejninzyy.Models.Cards.Ability;
import com.sergejninzyy.Models.Cards.Narod;
import com.sergejninzyy.Models.Cards.Unit;
import com.sergejninzyy.Models.Field;
import com.sergejninzyy.Models.Module_of_prediction;
import com.sergejninzyy.Models.Player;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int LVL = 2;

    public static void main(String[] args) throws IOException {

        GameObject gameObject = new GameObject(LVL);
        Player player = gameObject.getPlayer(0);
        Player intellect = gameObject.getPlayer(1);

        Module_of_prediction module_of_prediction = new Module_of_prediction();

        boolean result = game_with_player(gameObject, player, intellect, module_of_prediction);
        //boolean result = test_game(gameObject, player, intellect, module_of_prediction);


    }

    private static boolean test_game(GameObject gameObject, Player player, Player intellect, Module_of_prediction module_of_prediction) {
        setUnits_for_test_game(gameObject, player, intellect);

        System.out.println("Ход ИИ.");

        System.out.println("Состояние поля");
        System.out.println(gameObject.String());

        Date current = new Date();
        gameObject = module_of_prediction.predict(gameObject, 1, 3);
        System.out.println(GameObject.gameObjectcounter);
        System.out.println(new Date().getTime() - current.getTime() + " млсек");
        System.out.println("Состояние поля");
        System.out.println(gameObject.String());

        return true;
    }

    private static void setUnits_for_test_game(GameObject gameObject, Player player, Player intellect) {
        Unit unit_for_player = new Unit(Narod.CHEKATTA, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(0,2,-2));
        unit_for_player = new Unit(Narod.ITOSHIN, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(2,0,-2));


        Unit unit_for_ii = new Unit(Narod.DJUNIT, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(2, -2, 0));
        unit_for_ii = new Unit(Narod.CHEKATTA, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(-2, 0, 2));
        }

    private static void setUnits_for_game_with_player(GameObject gameObject, Player player, Player intellect) {
        Unit unit_for_player = new Unit(Narod.CHEKATTA, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(0,2,-2));
        unit_for_player = new Unit(Narod.ULUTAU, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(-1,2,-1));
        unit_for_player = new Unit(Narod.GUAVAR, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(1,1,-2));
        unit_for_player = new Unit(Narod.MECHNIC, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(0,1,-1));
        unit_for_player = new Unit(Narod.VEDICH, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(-2,2,0));
        unit_for_player = new Unit(Narod.TAVR, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(2,0,-2));


        Unit unit_for_ii = new Unit(Narod.ITOSHIN, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(0, -2, 2));
        unit_for_ii = new Unit(Narod.VEDICH, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(1, -2, 1));
        unit_for_ii = new Unit(Narod.GUAVAR, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(-1, -1, 2));
        unit_for_ii = new Unit(Narod.ULUTAU, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(0, -1, 1));
        unit_for_ii = new Unit(Narod.DJUNIT, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(2, -2, 0));
        unit_for_ii = new Unit(Narod.CHEKATTA, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(-2, 0, 2));



        /*Unit unit_for_player = new Unit(Narod.TAVR, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(0,2,-2));
        unit_for_player = new Unit(Narod.TAVR, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(0,1,-1));
        unit_for_player = new Unit(Narod.TAVR, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(-1,2,-1));
        unit_for_player = new Unit(Narod.TAVR, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(1,1,-2));
        unit_for_player = new Unit(Narod.TAVR, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(-2,2,0));
        unit_for_player = new Unit(Narod.TAVR, gameObject);
        gameObject.AddCardtoPlayeronFiels(player.getNumber(), unit_for_player, gameObject.FindField(2,0,-2));


        Unit unit_for_ii = new Unit(Narod.ITOSHIN, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(0, -2, 2));
        unit_for_ii = new Unit(Narod.ITOSHIN, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(0, -1, 1));
        unit_for_ii = new Unit(Narod.ITOSHIN, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(-1, -1, 2));
        unit_for_ii = new Unit(Narod.ITOSHIN, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(1, -2, 1));
        unit_for_ii = new Unit(Narod.ITOSHIN, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(-2, 0, 2));
        unit_for_ii = new Unit(Narod.ITOSHIN, gameObject);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),unit_for_ii, gameObject.FindField(2, -2, 0));*/
    }

    private static boolean game_with_player(GameObject gameObject, Player player, Player intellect, Module_of_prediction module_of_prediction) {
        setUnits_for_game_with_player(gameObject, player, intellect);

        System.out.println("Игра началась");
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (flag)
        {
            System.out.println("Введите кординаты юнита, которым хотите походить");
            Field players_field = gameObject.FindField(sc.nextInt(), sc.nextInt(), sc.nextInt());

            System.out.println("Возможные ходы");

            for( Pair<Pair<GameObject, Field>, Field> pair : module_of_prediction.possible_steps(players_field, gameObject))
            {
                Field pos_step = pair.getKey().getValue();
                System.out.println(pos_step.getCoordinats());
            }

            System.out.println("Введите клетку, куда хотите походить");
            Field step = gameObject.FindField(sc.nextInt(), sc.nextInt(), sc.nextInt());
            //походил
            gameObject.ChangeFieldofcard(players_field, step);

            //выберите способоность
            for(Ability ability : step.getUnit().getAbilities()) { System.out.println(ability); }
            //
            System.out.println("Напишите номер способности, который хотите применить (начиная с 0)");
            int num_of_ab = sc.nextInt();

            ArrayList<Field> arrayList = step.getAimofAbility(step.getUnit().getAbilities().get(num_of_ab), player, gameObject);

            if (arrayList.size() == 0)
            {
                System.out.println("У вас нет возможных применений своим способностям");
            }
            else {
                System.out.println("Введите координату клетки, на которую вы хотите применить свою способность");
                for (Field field: arrayList)
                {
                    System.out.println(field.getUnit().narod + " " + field.getUnit().hits +" "+ field.getCoordinats());
                }
                Field aim_of_abiliti = gameObject.FindField(sc.nextInt(), sc.nextInt(), sc.nextInt());

                gameObject.Action(step.getUnit().getAbilities().get(num_of_ab), step, aim_of_abiliti, 0);
            }

            System.out.println("Ход ИИ.");

            System.out.println("Состояние поля");
            System.out.println(gameObject.String());

            Date current = new Date();
            gameObject = module_of_prediction.predict(gameObject, 1, 3);
            System.out.println(GameObject.gameObjectcounter);
            System.out.println(new Date().getTime() - current.getTime() + " млсек");
            System.out.println("Состояние поля");
            System.out.println(gameObject.String());

            System.out.println("Сделаем еще один ход? Y/N");
            if (sc.next().compareTo("N") == 0 || sc.next().compareTo("n") == 0){
             flag = false;
            }
        }

        return true;
    }

    private static void SetRandomUnitonField (Player player, int x, int y, int z, GameObject gameObject)
    {
        gameObject.AddCardtoPlayeronFiels(player.getNumber(),RandomGenerateUnit(gameObject), gameObject.FindField(x, y, z));
    }

  /*  private static Field ChoseField(Player player, Unit unit) {
        //Показать игроку карту
        System.out.println(unit.narod);

        //спросить у игрока, куда он эту карту ставит
        System.out.println("Введите координаты клетки");
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();
        int z = sc.nextInt();

        Field field = gameObject.FindField(x, y, z);

        gameObject.AddCardtoPlayeronFiels(0, unit, field);
        return field;
    }
*/
    private static Unit RandomGenerateUnit(GameObject gameObject)
    {
        return new Unit(Narod.values()[new Random().nextInt(Narod.values().length)], gameObject);
    }

}
