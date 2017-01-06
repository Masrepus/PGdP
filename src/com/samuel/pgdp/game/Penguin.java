package com.samuel.pgdp.game;

public class Penguin extends Predator {

    // Ein Pinguin kann 12 Tage bzw. Spielrunden ohne Essen auskommen.
    // Die Deklaration darf entfernt (und der Wert z. B. direkt im Code
    // verwendet) werden.

    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Penguin(boolean female) {
        super(female);
        daysRemaining = 12;
    }

    /**
     * {@inheritDoc}
     * default for Penguin: 12
     */
    @Override
    protected void resetDaysRemaining() {
        daysRemaining = 12;
    }

    @Override
    public String toString(){
        return this.female
          ? (Globals.darkSquare(this.square) ? Globals.ts_female_penguin_dark : Globals.ts_female_penguin_light)
          : (Globals.darkSquare(this.square) ? Globals.ts_male_penguin_dark : Globals.ts_male_penguin_light);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move[] possibleMoves() {
        return super.possibleMoves();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Move> getRawPossibleMoves() {
        //possible moves: one field in either direction
        List<Move> tmp = new List<>();
        char column = square.charAt(0);
        char row = square.charAt(1);

        //one up, down, right and left
        addMove(tmp, 0, 1);
        addMove(tmp, 0, -1);
        addMove(tmp, 1, 0);
        addMove(tmp, -1, 0);

        //diagonal
        addMove(tmp, 1, 1);
        addMove(tmp, 1, -1);
        addMove(tmp, -1, 1);
        addMove(tmp, -1, -1);

        //now eliminate all moves whose destinations are not on the board
        return removeOffScreenMoves(tmp);
    }

    /**
     * @return always true because a penguin has no intermediate fields
     */
    @Override
    protected boolean checkIntermediateFields(List<Move> possibleMoves, Move move) {
        //a penguin has no intermediate fields
        return true;
    }
}
