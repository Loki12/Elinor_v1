package com.sergejninzyy;

import com.sergejninzyy.Models.Cards.Narod;
import com.sergejninzyy.Models.Cards.Unit;
import com.sergejninzyy.Models.Module_of_prediction;
import com.sergejninzyy.Models.Player;

import java.io.*;
import java.util.Random;

public class Main {

    private static final int LVL = 2;
    //public static GameObject gameObject = new GameObject(LVL);
    //Player player = gameObject.getPlayer(0);
    //Player intellect = gameObject.getPlayer(1);

    public static void main(String[] args) throws IOException {

        GameObject gameObject = new GameObject(LVL);
        Player player = gameObject.getPlayer(0);
        Player intellect = gameObject.getPlayer(1);

      /*  System.out.println("Игра началась");

        for (int i = 0; i < 6; i++) {

            //игрок выбирает поле
            System.out.println("Ваши координаты - " + ChoseField(player, RandomGenerateUnit()).getCoordinats() + "\n", gameobject);

            //показать ии карту и спросить куда он ее ставит
            Player intellect = gameObject.getPlayer(1);
            if ( intellect instanceof Intellect)
            {
                Unit unit = RandomGenerateUnit();
                Field curr_field = ((Intellect) intellect).AlignmentSet(unit, i);
                System.out.println(unit.narod);
                System.out.println(curr_field.getX() + " " + curr_field.getY()+ " " + " " +curr_field.getZ());
                gameObject.AddCardtoPlayeronFiels(1, unit, curr_field);
            }
        }*/
      //Test Game
        //Для игрока
        SetRandomUnitonField(player, 0, 2,-2, gameObject);
        SetRandomUnitonField(player, -1, 2, -1, gameObject);
        SetRandomUnitonField(player, 1, 1, -2, gameObject);
        SetRandomUnitonField(player, 0, 1, -1, gameObject);
        SetRandomUnitonField(player, -2, 2, 0, gameObject);
        SetRandomUnitonField(player, 2, 0, -2, gameObject);

        //Для ИИ
        /*SetRandomUnitonField(intellect, 0, -2,2);
        SetRandomUnitonField(intellect, 1, -2, 1);
        SetRandomUnitonField(intellect, -1, -1, 2);*/

        Unit ito = new Unit(Narod.ITOSHIN);
        gameObject.AddCardtoPlayeronFiels(intellect.getNumber(),ito, gameObject.FindField(0, 0, 0));
        //SetRandomUnitonField(intellect, 0, -1, 1, gameObject);
        /*SetRandomUnitonField(intellect, 2, -2, 0);
        SetRandomUnitonField(intellect, -2, 0, 2);*/

        System.out.println("Игра началась");

       /* System.out.println(gameObject.Step(gameObject.FindField(-2,2,0), gameObject.FindField(-2, 1,1),
                gameObject.FindField(-2, 0, 2), Ability.ATTACK, 0));*/

        //я даю поле откуда, поле куда походили, gameobject
        //задача ии - взять все возможные дейтсвия юнита, пройтись по всем, скопировать gameobject для каждого дейтсвия,
        // сделать ход и дейтвие, вернуть пару gameobject unteger

        //gameObject.find_actions()
        Module_of_prediction module_of_prediction = new Module_of_prediction();
        GameObject new_gameObject = module_of_prediction.predict(gameObject, 1, 2);
    }

    private static void SetRandomUnitonField (Player player, int x, int y, int z, GameObject gameObject)
    {
        Unit unit = RandomGenerateUnit();
        gameObject.AddCardtoPlayeronFiels(player.getNumber(),unit, gameObject.FindField(x, y, z));
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
    private static Unit RandomGenerateUnit()
    {
        return new Unit(Narod.values()[new Random().nextInt(Narod.values().length)]);
    }

  /*  private static Unit RandomGenerateUnitreturn(Unit unit)
    {
        return new Unit(Narod.values()[new Random().nextInt(Narod.values().length)]);
    }
*/


}
