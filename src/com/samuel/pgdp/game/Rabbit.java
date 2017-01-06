package com.samuel.pgdp.game;

public class Rabbit extends Vegetarian {

    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Rabbit(boolean female) {
        super(female);
    }


    @Override
    public String toString(){
        return this.female
          ? (Globals.darkSquare(this.square) ? Globals.ts_female_rabbit_dark : Globals.ts_female_rabbit_light)
          : (Globals.darkSquare(this.square) ? Globals.ts_male_rabbit_dark : Globals.ts_male_rabbit_light);
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
     * @return always true because a rabbit has no intermediate fields
     */
    @Override
    protected boolean checkIntermediateFields(List<Move> possibleMoves, Move move) {
        //a rabbit has no intermediate fields
        return true;
    }

}
