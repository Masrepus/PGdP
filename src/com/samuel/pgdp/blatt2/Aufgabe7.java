package com.samuel.pgdp.blatt2;

import com.samuel.pgdp.MiniJava;

public class Aufgabe7 extends MiniJava{

    public Aufgabe7(int endMonth) {

        //enter version 1 or 2 for assignment 2.7.1 or 2.7.2
        int version = readInt("Choose the subtask: 1 or 2");
        while (version != 1 && version != 2) {
            write(version + " is invalid!");
            version = readInt("Choose the subtask: 1 or 2");
        }

        if (version == 1) startSimulation(endMonth, false);
        else startSimulation(endMonth, true);
    }

    public static void main(String[] args) {

        //enter month > 0
        int endMonth = readInt("Choose the month (>0):");
        while (endMonth <= 0) {
            write(endMonth + " ist ungültig!");
            endMonth = readInt("Choose the month (>0):");
        }

        //avoid having to use static methods
        new Aufgabe7(endMonth);
    }

    /**
     * rabbit simulation for assignment nr. 2.7
     * @param endMonth the month the user wants to see the number of rabbit couples for
     * @param subtask2 whether or not we should use subtask 2 algorithm: if true, couples in gen2 give birth to 3 new couples instead of 1
     */
    private void startSimulation(int endMonth, boolean subtask2) {

        //starting with 2 rabbits, rabbits1, rabbits2, rabbits3 are the number of rabbit couples in generation 1, 2 and 3
        long rabbits1 = 1;
        long rabbits2 = 0;
        long rabbits3 = 0;

        //now iterate through the months and calculate the number of rabbits
        for (int i = 2; i <= endMonth; i++) {

            //first calculate the number of newly born rabbit couples: each existing couple gives birth to one new one, unless subtask2 is set
            long newBorns;
            if (subtask2) newBorns = rabbits1 + 3*rabbits2 + rabbits3;
            else newBorns = rabbits1 + rabbits2 + rabbits3;

            //now shift each generation to the next older one, gen 3 gets dismissed
            rabbits3 = rabbits2;
            rabbits2 = rabbits1;
            rabbits1 = newBorns; //now insert the newborns as gen 1
        }

        //output the result
        long total = rabbits1 + rabbits2 + rabbits3;
        write(String.format("Rabbit couples in month %1$d: %2$d \n" +
                "Rabbit couples per generation: %3$d in gen 1, %4$d in gen 2, %5$d in gen 3", endMonth, total, rabbits1, rabbits2, rabbits3));
    }
}
