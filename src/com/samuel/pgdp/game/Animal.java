package com.samuel.pgdp.game;

/**
 * Oberklasse fuer Tiere.
 */
public class Animal {

    // Attribute fuer den allen Tieren gemeinen Teil des Tierzustands
    public boolean female; // Weibchen?
    public boolean alive;  // Lebendig?
    public String square;  // Auf welchem Feld? (genau zwei Zeichen, z. B. "e4")
    public Position position; // Auf welchem Spielbrett?


    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Animal(boolean female) {
        this.female = female;
        alive = true;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getSquare() {
        return square;
    }

    /**
     * Ermittelt die moeglichen Zuege gemaess den Spielregeln,
     * die das Tier von seinem Feld aus in der aktuellen Position
     * ausfuehren kann.
     *
     * Muss von jeder einzelnen Tierklasse ueberschrieben werden.
     */
    public Move[] possibleMoves(){
        return null;
    }


    /**
     * Wird aufgerufen nach jeder Spielrunde
     * (quasi am Ende vom Tag - jede Spielrunde zaehlt als ein Tag).
     *
     *  Muss in jeder einzelnen Tierklasse ueberschrieben sein!
     */
    public void sunset(){
        // Methode (und Klasse Animal) sollten eigentlich als abstract deklariert sein.
        // Kommt spaeter in der Vorlesung noch dran...
        // Zum Verstaendnis reicht es, dass diese Methode ueberschrieben werden muss.
        // (Die folgende Zeile wird dann auch nie ausgefuehrt.)
        throw new RuntimeException("Method sunset should have been overridden");
    }

    public void kill() {
        alive = false;
        position.kill(square);
    }

    public String getGender() {
        return female ? "female" : "male";
    }

    public boolean isMoveLegal(String destination) {
        //check if there is another animal at our destination
        Animal targetAnimal = position.getAnimal(destination);
        if (targetAnimal != null) {
            //we have an animal there, so we can only move there if we are a predator and the target is a vegetarian, AND if the target is an enemy
            if (targetAnimal.getGender().equals(getGender())) return false;
            if (this instanceof Vegetarian || targetAnimal instanceof Predator) return false;
        }

        //destination field is either clear or we can eat the target
        //now check whether this animal can actually reach this square
        List<Move> possibleMoves = getPossibleMoves();
        for (int i = 0; i < possibleMoves.length(); i++) {
            if (possibleMoves.get(i).getTo().equals(destination)) return true;
        }

        //doesn't seem like this move is legal
        return false;
    }

    /**
     * Finds all moves that are technically possible for this animal. Doesn't take into account the legality of any moves that point to an occupied field. The caller has to handlle this himself. Moves that have an off-board destination are dismissed by this method
     *
     * @return a {@link List} of moves that are possible for this animal
     */
    protected List<Move> getPossibleMoves() {
        //has to be overridden by child classes
        return new List<>();
    }
}
