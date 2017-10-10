package com.sergejninzyy;

import com.sergejninzyy.Models.Cards.Narod;
import com.sergejninzyy.Models.Cards.Unit;
import com.sergejninzyy.Models.Field;
import com.sergejninzyy.Models.Intellect;
import com.sergejninzyy.Models.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static GameObject gameObject = new GameObject();
    public static Player player = gameObject.getPlayer(0);
    public static Player intellect = gameObject.getPlayer(1);

    public static void main(String[] args) throws IOException {

        System.out.println("Игра началась");

        for (int i = 0; i < 6; i++) {

            //игрок выбирает поле
            System.out.println("Ваши координаты - " + ChoseField(player, RandomGenerateUnit()).getCoordinats() + "\n");

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
        }



        //Тесты соседей
        /*
        ArrayList<Field> neighbours = gameObject.FindField(0, 2, -2).GetNeighbours(1);
        for (Field f: neighbours) {
            System.out.println(f.getX() + " " + f.getY() + " " + f.getZ());
        }*/

    }

    private static Field ChoseField(Player player, Unit unit) {
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

    private static Unit RandomGenerateUnit()
    {
        return new Unit(Narod.values()[new Random().nextInt(Narod.values().length)]);
    }

    private static Unit RandomGenerateUnitreturn(Unit unit)
    {
        return new Unit(Narod.values()[new Random().nextInt(Narod.values().length)]);
    }



}
