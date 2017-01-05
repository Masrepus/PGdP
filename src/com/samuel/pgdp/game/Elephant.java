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
    public Move[] possibleMoves() {
        return super.possibleMoves();
    }

    @Override
    public String toString(){
        return this.female
          ? (Globals.darkSquare(this.square) ? Globals.ts_female_elephant_dark : Globals.ts_female_elephant_light)
          : (Globals.darkSquare(this.square) ? Globals.ts_male_elephant_dark : Globals.ts_male_elephant_light);
    }

    @Override
    protected List<Move> getRawPossibleMoves() {
        List<Move> moves = new List<>();
        char column = square.charAt(0);
        char row = square.charAt(1);
        int moveSequence = 0;

        //up
        for (char r = (char) (row + 1); r <= '8'; r++) {
            Move move = new Move("" + column + row, "" + column + r);
            move.setMoveSequence(moveSequence);
            moves.add(move);
        }

        //down
        moveSequence++;

        for (char r = (char) (row - 1); r >= '1'; r--) {
            Move move = new Move("" + column + row, "" + column + r);
            move.setMoveSequence(moveSequence);
            moves.add(move);
        }

        //right
        moveSequence++;

        for (char c = (char) (column + 1); c <= 'h'; c++) {
            Move move = new Move("" + column + row, "" + c + row);
            move.setMoveSequence(moveSequence);
            moves.add(move);
        }

        //left
        moveSequence++;

        for (char c = (char) (column - 1); c >= 'a'; c--) {
            Move move = new Move("" + column + row, "" + c + row);
            move.setMoveSequence(moveSequence);
            moves.add(move);
        }

        //we're done
        return moves;
    }
}
