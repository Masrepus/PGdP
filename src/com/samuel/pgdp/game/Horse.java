package com.samuel.pgdp.game;

import com.samuel.pgdp.List;

public class Horse extends Vegetarian {

    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Horse(boolean female) {
        super(female);
    }


    @Override
    public String toString(){
        return this.female
          ? (Globals.darkSquare(this.square) ? Globals.ts_female_horse_dark : Globals.ts_female_horse_light)
          : (Globals.darkSquare(this.square) ? Globals.ts_male_horse_dark : Globals.ts_male_horse_light);
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
     *
     * @return always true because a horse has no intermediate fields
     */
    @Override
    protected boolean checkIntermediateFields(List<Move> possibleMoves, Move move) {
        //a horse has no intermediate fields
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Move> getRawPossibleMoves() {
        List<Move> moves = new List<>();

        //one up, down, right and left
        addMove(moves, 0, 1);
        addMove(moves, 0, -1);
        addMove(moves, 1, 0);
        addMove(moves, -1, 0);

        //two diagonally up+left, up+right, down+left, down+right
        addMove(moves, -2, 2);
        addMove(moves, 2, 2);
        addMove(moves, -2, -2);
        addMove(moves, 2, -2);

        //three diagonally
        addMove(moves, -3, 3);
        addMove(moves, 3, 3);
        addMove(moves, -3, -3);
        addMove(moves, 3, -3);

        //done
        return removeOffScreenMoves(moves);
    }
}
