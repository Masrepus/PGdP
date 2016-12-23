package com.samuel.pgdp.game;

/**
 * Die Klasse Position repraesentiert eine Spielsituation.
 *
 */
public class Position {

    /**
     * Die Tiere werden intern in einem Array gespeichert.
     * nrAnimals gibt an, wie viele Tiere auf dem Brett sind.
     * Diese sind in myAnimals an den Positionen 0 bis nrAnimals-1 enthalten.
     *
     * Es ist empfohlen, aber nicht vorgeschrieben, diese Attribute zu verwenden.
     *
     * Falls die beiden Attribute NICHT verwendet werden, muss die Ausgabe
     * der Spielposition unten entsprechend auf die verwendete Datenstruktur
     * angepasst werden. Die toString-Methode darf dabei nicht veraendert werden,
     * muss jedoch die selbe Rueckgabe liefern. D.h. es ist dann notwendig,
     * die Hilfsmethode boardRepresentation auf die verwendete Datenstruktur anzupassen.
     */
    private Animal[] myAnimals;
    private int nrAnimals;
    public static final String backRow = "sehllhes";
    public static final String frontRow = "pkkkkkkp";

    /**
     * Spieler, der als naechstes ziehen darf ('M' oder 'W').
     * Wird jedes Mal aktualisiert, wenn eine Seite ihre Zuege ausfuehrt.
     */
    private char next = 'W';


    /**
     * Stellt die Anfangsposition des Spiels her.
     * Der Parameter gibt an, welche Seite beginnt ('M' oder 'W').
     */
    public void reset(char movesNext) {
        next = movesNext;

        //we have 16 animals for each team
        nrAnimals = 32;

        //init the animal positions
        myAnimals = new Animal[32];
        //females front row
        int nextAnimal = initAnimals(true, true, 0);
        //females back row
        nextAnimal = initAnimals(true, false, nextAnimal);
        //males front row
        nextAnimal = initAnimals(false, true, nextAnimal);
        //males back row
        initAnimals(false, false, nextAnimal);
    }

    /**
     * Sets one row of animals to the default value for a given gender
     *
     * @param females     whether a row of females or a row of males should be initialized
     * @param useFrontRow whether the front row or the back row of animals is to be initialized
     * @param currAnimal  the first place in myAnimals that is currently empty
     * @return the next empty position in myAnimals after this initialization
     */
    private int initAnimals(boolean females, boolean useFrontRow, int currAnimal) {
        //both parties have the same animals in their front and back row, but the row numbers are different
        int front = !females ? 7 : 2;
        int back = !females ? 8 : 1;

        //start with front row
        char column = 'a';
        int row = useFrontRow ? front : back;

        for (int i = 0; i < (useFrontRow ? frontRow : backRow).length(); i++) {

            Animal animal = new Animal(females);

            //set the animal's race
            switch ((useFrontRow ? frontRow : backRow).charAt(i)) {
                case 'k':
                    animal = new Rabbit(females);
                    break;
                case 'p':
                    animal = new Penguin(females);
                    break;
                case 's':
                    animal = new Snake(females);
                    break;
                case 'e':
                    animal = new Elephant(females);
                    break;
                case 'h':
                    animal = new Horse(females);
                    break;
                case 'l':
                    animal = new Leopard(females);
                    break;
            }

            //now set the curent position
            animal.setSquare("" + column + row);

            //done, now we can add it
            myAnimals[currAnimal] = animal;
            currAnimal++;
            column++;
        }

        //return the next animal id
        return currAnimal;
    }


    /**
     * Fuehrt die uebergebenen Zuege fuer einen der Spieler aus.
     * Die Reihenfolge soll keinen Unterschied machen.
     * Diese Methode geht davon aus, dass dies bereits ueberprueft wurde.
     *
     * Der Zustand des Spiels wird entsprechend angepasst, d. h. ein Spiel
     * kann von der Anfangsposition aus allein mittels Aufrufen dieser Methode
     * gespielt werden. Insbesondere wechselt durch den Aufruf das Zugrecht,
     * da M und W abwechselnd ziehen.
     *
     * @param move Array mit den Zuegen, die ausgefuehrt werden sollen.
     *
     */
    public void applyMoves(Move[] move){
        //TODO
    }


    /**
     * Ermittelt, ob/wer gewonnen hat.
     *
     * @return 'W' falls W gewonnen hat,
     *         'M' falls M gewonnen hat,
     *         'N' falls das Spiel unentschieden zu Ende ist,
     *         'X' falls das Spiel noch nicht zu Ende ist.
     *
     */
    public char theWinner() {
        return 'X'; //TODO
    }





    // Ausgabe der Spielposition

    private static final int[] I = {8,7,6,5,4,3,2,1};
    private static final String[] J = {"a","b","c","d","e","f","g","h"};
    private static int toIndex(String s){return (s.charAt(0)-'a');}

    // Erzeugt eine 2D-Repraesentation der Spielposition.
    // Darf ggf. auf neue Datenstruktur angepasst werden (s.o.)
    // Die Rueckgabe ist ein zweidimensionales Array, welches
    // jedem Feld das darauf befindliche Tier (oder null) zuordnet.
    // Dabei laeuft der erste Index von der 'a'-Linie zur 'h'-Linie,
    // der zweite von der 1. zur 8. Reihe. D.h. wenn z.B. bei a[3][7]
    // ein Tier ist, ist das zugehörige Feld "d8" (vierter Buchstabe,
    // achte Zahl).
    public Animal[][] boardRepresentation(){
        Animal[][] a = new Animal[8][8];
        for (int i : I) {
            for (String j : J) {
                for (int k = 0; k < myAnimals.length; k++) {
                    if (null == myAnimals[k]) {break;}
                    if (myAnimals[k].square.equals(j+i)) {
                        a[toIndex(j)][i-1] = myAnimals[k];
                    }
                }
            }
        }
        return a;
    }


    @Override
    public String toString(){
        String str = "   a b c d e f g h\n";
        Animal[][] ani = boardRepresentation();
        for (int i : I) {
            str += (i+" ");
            for (String j : J) {
                if (null == ani[toIndex(j)][i-1]) {
                    str += (i+toIndex(j))%2==1 ? Globals.ts_empty_square_dark : Globals.ts_empty_square_light;
                } else {
                    str += ani[toIndex(j)][i-1].toString();
                }
            }
            str += " " + i + "\n";
        }
        str += "  a b c d e f g h\nIt is " + next + "'s turn.\n";
        return str;
    }

}
