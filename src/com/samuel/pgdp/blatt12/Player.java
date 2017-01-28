package com.samuel.pgdp.blatt12;

import java.util.Random;

/**
 * Created by Samuel on 23.01.2017.
 */
public class Player implements Runnable {

    public static final int SCISSORS = 0;
    public static final int STONE = 1;
    public static final int PAPER = 2;

    private int choice;
    private boolean newValueRequested;

    @Override
    public void run() {
        while (true) {
            while (!newValueRequested) {
                Thread.yield();
            }

            //new value has been requested
            choice = new Random().nextInt(3);
            newValueRequested = false;
        }
    }

    public int getChoice() throws InterruptedException {
        //wait until we're done
        newValueRequested = true;
        while (newValueRequested) {
            Thread.yield();
        }

        return choice;
    }
}
