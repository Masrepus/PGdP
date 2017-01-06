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

    /**
     * On sunset, a predator gets one day less that it can survive without food. If the predator has no days left, it has to eat in the next round. If it doesn't, {@link #daysRemaining} hits -1 and then the predator dies. This invokes {@link Animal#kill()}
     */
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

    /**
     * Resets {@link #daysRemaining} to the default value for the specific predator
     */
    protected void resetDaysRemaining() {
        //will be overridden in child classes
    }

    /**
     * Called when a predator lands on the field of a vegetarian. The predator kills the vegetarian. This invokes {@link Animal#kill()} for the vegetarian and {@link Predator#resetDaysRemaining()} for the predator
     *
     * @param targetAnimal
     */
    public void eat(Animal targetAnimal) {
        //kill the animal and reset the days without food
        position.kill(targetAnimal.getSquare());
        resetDaysRemaining();
    }
}
