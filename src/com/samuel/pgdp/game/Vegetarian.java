package com.samuel.pgdp.game;

/**
 * Klasse der Vegetarier.
 */
public class Vegetarian extends Animal {

    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Vegetarian(boolean female) {
        super(female);
    }

    /**
     * A vegetarian doesn't care about sunset
     */
    @Override
    public void sunset() {
        //do nothing
    }
}
