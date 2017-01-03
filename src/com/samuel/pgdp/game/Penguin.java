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
}
