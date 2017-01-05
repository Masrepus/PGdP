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

    @Override
    public Move[] possibleMoves() {
        return super.possibleMoves();
    }

    @Override
    protected List<Move> getRawPossibleMoves() {
        //possible moves: one field in either direction
        List<Move> tmp = new List<>();
        char column = square.charAt(0);
        char row = square.charAt(1);

        //one to the right
        tmp.add(new Move(square, "" + (char) (column + 1) + row));
        //one to the left
        tmp.add(new Move(square, "" + (char) (column - 1) + row));
        //one up
        tmp.add(new Move(square, "" + column + (char) (row + 1)));
        //one down
        tmp.add(new Move(square, "" + column + (char) (row - 1)));

        //up and right
        tmp.add(new Move(square, "" + (char) (column + 1) + (char) (row + 1)));
        //down and right
        tmp.add(new Move(square, "" + (char) (column - 1) + (char) (row + 1)));
        //up and left
        tmp.add(new Move(square, "" + (char) (column + 1) + (char) (row - 1)));
        //down and left
        tmp.add(new Move(square, "" + (char) (column - 1) + (char) (row - 1)));

        //now eliminate all moves whose destinations are not on the board
        List<Move> moves = new List<>();
        for (int i = 0; i < tmp.length(); i++) {
            Move move = tmp.get(i);
            column = move.getTo().charAt(0);
            row = move.getTo().charAt(1);
            if (column >= 'a' && column <= 'h' && row >= '1' && row <= '8') moves.add(move);
        }

        //now we got everything
        return moves;
    }

    @Override
    protected boolean checkIntermediateFields(List<Move> possibleMoves, Move move) {
        //a penguin has no intermediate fields
        return true;
    }

}
