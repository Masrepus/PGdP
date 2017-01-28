package com.samuel.pgdp.blatt12.rock;

/**
 * Created by Samuel on 23.01.2017.
 */
public class RockPaperScissors implements Runnable {

    public static final int SCISSORS = 0;
    public static final int STONE = 1;
    public static final int PAPER = 2;

    public static void main(String[] args) {
        Player p1 = new Player();
        Player p2 = new Player();
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        t1.start();
        t2.start();

        int i = 1000;
        int p1Win = 0, p2Win = 0, tie = 0;

        while (i > 0) {
            try {
                int p1Choice = p1.getChoice();
                int p2Choice = p2.getChoice();

                //check who won
                int winner = determineWinner(p1Choice, p2Choice);
                if (winner == 0) tie++;
                else if (winner == 1) p1Win++;
                else p2Win++;

                i--;

            } catch (InterruptedException e) {
                System.out.println("unexpected Exception while playing in round " + (1000 - i));
            }
        }

        //done, stop the threads
        t1.interrupt();
        t2.interrupt();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("unexpected Exception while joining the threads");
        }

        System.out.println("Player 1 won " + p1Win + " times, player 2 " + p2Win + " times. There were " + tie + " ties.\n" +
                "Total games played: " + (p1Win + p2Win + tie));
    }

    @Override
    public void run() {
        System.out.println();
    }

    public static int determineWinner(int p1Choice, int p2Choice) {
        if (p1Choice == PAPER) {
            if (p2Choice == PAPER) return 0;
            else if (p2Choice == STONE) return 1;
            else return 2;
        } else if (p1Choice == STONE) {
            if (p2Choice == STONE) return 0;
            else if (p2Choice == SCISSORS) return 1;
            else return 2;
        } else if (p1Choice == SCISSORS) {
            if (p2Choice == SCISSORS) return 0;
            else if (p2Choice == PAPER) return 1;
            else return 2;
        }

        return 0;
    }
}
