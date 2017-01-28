package com.samuel.pgdp.blatt12.rock;

import java.util.Random;

/**
 * Created by Samuel on 23.01.2017.
 */
public class Player implements Runnable {

    private int choice;
    private boolean newChoiceAvailable = false;
    private final Object lockStorage = new Object();
    private final Object lockChoice = new Object();
    private Random random = new Random();

    @Override
    public void run() {
        while (true) {
            try {
                calculateChoice();
            } catch (InterruptedException e) {
                System.out.println("Player ended");
                break;
            }
        }
    }

    private void calculateChoice() throws InterruptedException {
        synchronized (lockStorage) {
            while (newChoiceAvailable) {
                lockStorage.wait();
            }

            //new choice has been requested
            choice = random.nextInt(3);
            newChoiceAvailable = true;
        }

        //notify any waiting getChoice callers
        synchronized (lockChoice) {
            lockChoice.notify();
        }
    }

    public int getChoice() throws InterruptedException {
        //wait until we're done calculating the new value
        synchronized (lockChoice) {
            while (!newChoiceAvailable) {
                lockChoice.wait();
            }

            int currChoice = choice;
            newChoiceAvailable = false;
            //notify the calculator that we need a new choice
            synchronized (lockStorage) {
                lockStorage.notify();
            }
            return currChoice;
        }
    }
}
