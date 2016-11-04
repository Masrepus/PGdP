package com.samuel.pgdp.blatt2;

import com.samuel.pgdp.MiniJava;

public class Aufgabe8 extends MiniJava{

    public static void main(String[] args) {
        new Aufgabe8();
    }

    public Aufgabe8() {

        //only numbers >0 are allowed
        int maxNumber = readInt("Choose the maximum number:");
        while (maxNumber <= 0) {
            write(maxNumber + " is invalid!");
            maxNumber = readInt("Choose the maximum number:");
        }

        printTable(maxNumber);
    }

    private int ggt(int x, int y) {

        //ggt algorithm as shown in EIDI1 lecture
        if (x == y) return y;
        else if (x > y) return ggt(x-y, y);
        else return ggt(x, y-x);
    }

    private void printTable(int maxNumber) {

        //first print the header row, three tabs indent
        System.out.print("\t\t");
        for (int i = 1; i <= maxNumber; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        System.out.println();

        //go through all table rows
        for (int i = 1; i <= maxNumber; i++) {

            //first print the current row's number
            System.out.print(i + "\t\t");

            //go through all table columns
            for (int k = 1; k <= maxNumber; k++) {
                System.out.print(ggt(i,k) + "\t");
            }

            //next row
            System.out.println();
        }
    }
}
