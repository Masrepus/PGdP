package com.samuel.pgdp.game;

/**
 * Die Klasse Main enthaelt das Hauptprogramm.
 *
 * Im Hauptprogramm wird zuerst der Benutzer gefragt,
 * wer das Spiel beginnen soll.
 *
 * Dann wird das Spiel gestartet.
 *
 */
public class Main {

    public static void main(String args[]) {
        Game game = new Game();
        game.startGame(ladiesFirst());
    }

    private static boolean ladiesFirst() {
        //ask who should begin
        String beginner = IO.readString("Which player should begin? Type w or m\n");
        do {
            //check if the user entered w, W, m or M
            switch (beginner) {
                case "w":
                case "W":
                    //ladies first
                    return true;
                case "m":
                case "M":
                    //m begins
                    return false;
            }

            //seems like the user's input is not legal
            beginner = IO.readString(beginner + " is not a legal input!\n" +
                    "Which player should begin? Type w or m\n");
        } while (true);
    }

}
