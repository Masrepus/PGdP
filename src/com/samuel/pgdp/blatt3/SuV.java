package com.samuel.pgdp.blatt3;

import com.samuel.pgdp.MiniJava;

public class SuV extends MiniJava{

    public static void main(String[] args) {
        new SuV();
    }

    public SuV() {
        int player1 = play(1);
        int player2 = play(2);

        //evaluate who has won and write it
        if (player1 > player2) {
            if (player1 > 21) write("Player 2 has won! (Player 1 has more than 21 points) " + player2 + ":" + player1 + " points");
            else write("Player 1 has won! " + player1 + ":" + player2 + " points");
        }
        if (player1 == player2) write("Player 1 wins! (equal amount of points)" + player1 + ":" + player2 + " points");
        if (player1 < player2) {
            if (player2 > 21) write("Player 1 has won! (Player 2 has more than 21 points) " + player1 + ":" + player2 + " points");
            else write("Player 2 has won! " + player2 + ":" + player1 + " points");
        }
    }

    private int play(int player) {

        //give him his first two cards
        int cards = drawCard();
        cards += drawCard();

        //draw cards until the player tells us to stop
        //tell player1 his card value and ask, if he would like another one

        while (true) {
            if (cards > 21) {
                //instant loss
                write("Player " + player + "! The value of your cards is: " + cards + "\nYou lost!");
                break;
            }
            int newCard = readInt("Player " + player + "! The value of your cards is: " + cards
                    + ", \nwould you like the dealer to give you another one?" +
                    "\nEnter 1 if yes, 0 if not");

            //only 1 or 0 are valid input values
            while (newCard != 0 && newCard != 1) {
                write(newCard + " is invalid!");
                newCard = readInt("Player " + player + "! The value of your cards is: " + cards
                        + ", \nwould you like the dealer to give you another one?" +
                        "\nEnter 1 if yes, 0 if not");
            }

            if (newCard == 1) cards += drawCard();
            else break;
        }

        return cards;
    }
}
