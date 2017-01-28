package com.samuel.pgdp.blatt12;

/**
 * Created by Samuel on 23.01.2017.
 */
public class RockPaperScissors implements Runnable {

    public static void main(String[] args) {
        Player player = new Player();
        new Thread(player).start();

        while (true) {
            try {
                System.out.println(player.getChoice());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        System.out.println();
    }
}
