package com.samuel.pgdp.game;

public class Elephant extends Vegetarian {

    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Elephant(boolean female) {
        super(female);
    }


    @Override
    public String toString(){
        return this.female
          ? (Globals.darkSquare(this.square) ? Globals.ts_female_elephant_dark : Globals.ts_female_elephant_light)
          : (Globals.darkSquare(this.square) ? Globals.ts_male_elephant_dark : Globals.ts_male_elephant_light);
    }

}
