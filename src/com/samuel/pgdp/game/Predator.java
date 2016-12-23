package com.samuel.pgdp.game;

/**
 * Klasse der Raubtiere.
 */
public class Predator extends Animal {

    protected int daysRemaining;

    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Predator(boolean female) {
        super(female);
    }

    @Override
    public void sunset() {
        daysRemaining--;

        //check if this animal dies now: if daysRemaining == 0 the animal still has one round to live
        if (daysRemaining == -1) {
            kill();
        }
    }

    public int getDaysRemaining() {
        return daysRemaining;
    }
}
