package com.samuel.pgdp.game;

public class Elephant extends Vegetarian {

    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Elephant(boolean female) {
        super(female);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move[] possibleMoves() {
        return super.possibleMoves();
    }

    @Override
    public String toString(){
        return this.female
          ? (Globals.darkSquare(this.square) ? Globals.ts_female_elephant_dark : Globals.ts_female_elephant_light)
          : (Globals.darkSquare(this.square) ? Globals.ts_male_elephant_dark : Globals.ts_male_elephant_light);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Move> getRawPossibleMoves() {
        List<Move> moves = new List<>();
        char column = square.charAt(0);
        char row = square.charAt(1);
        int moveSequence = 0;

        //up
        for (int rowOffset = 1; (char) (row + rowOffset) <= '8'; rowOffset++) {
            addMove(moves, 0, rowOffset, moveSequence);
        }

        //down
        moveSequence++;

        for (int rowOffset = -1; (char) (row + rowOffset) >= '1'; rowOffset--) {
            addMove(moves, 0, rowOffset, moveSequence);
        }

        //right
        moveSequence++;

        for (int columnOffset = 1; (char) (column + columnOffset) <= 'h'; columnOffset++) {
            addMove(moves, columnOffset, 0, moveSequence);
        }

        //left
        moveSequence++;

        for (int columnOffset = -1; (char) (column + columnOffset) >= 'a'; columnOffset--) {
            addMove(moves, columnOffset, 0, moveSequence);
        }

        //we're done
        return removeOffScreenMoveSequences(moves);
    }
}
