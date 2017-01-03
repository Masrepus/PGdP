package com.samuel.pgdp.game;

public class Snake extends Predator {

    // Eine Schlange kann 9 Tage bzw. Spielrunden ohne Essen auskommen.
    // Die Deklaration darf entfernt (und der Wert z. B. direkt im Code
    // verwendet) werden.


    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Snake(boolean female) {
        super(female);
        daysRemaining = 9;
    }

    @Override
    protected void resetDaysRemaining() {
        daysRemaining = 9;
    }

    @Override
    public String toString(){
        return this.female
          ? (Globals.darkSquare(this.square) ? Globals.ts_female_snake_dark : Globals.ts_female_snake_light)
          : (Globals.darkSquare(this.square) ? Globals.ts_male_snake_dark : Globals.ts_male_snake_light);
    }

    @Override
    public Move[] possibleMoves() {
        return super.possibleMoves();
    }

    @Override
    protected List<Move> getRawPossibleMoves() {
        List<Move> moves = new List<>();
        char column = square.charAt(0);
        char row = square.charAt(1);

        boolean originalRow, originalColumn;

        //up, alternate columns
        originalColumn = false;

        for (char r = (char) (row + 1); r <= '8'; r++) {
            if (originalColumn) moves.add(new Move(square, "" + column + r));
            else moves.add(new Move(square, "" + (char) (column - 1) + r));

            originalColumn = !originalColumn;
        }

        //down
        originalColumn = false;

        for (char r = (char) (row - 1); r >= '1'; r--) {
            if (originalColumn) moves.add(new Move(square, "" + column + r));
            else moves.add(new Move(square, "" + (char) (column + 1) + r));

            originalColumn = !originalColumn;
        }

        //left
        originalRow = false;

        for (char c = (char) (column - 1); c >= 'a'; c--) {
            if (originalRow) moves.add(new Move(square, "" + c + row));
            else moves.add(new Move(square, "" + c + (char) (row - 1)));

            originalRow = !originalRow;
        }

        //right
        originalRow = false;

        for (char c = (char) (column + 1); c <= 'h'; c++) {
            if (originalRow) moves.add(new Move(square, "" + c + row));
            else moves.add(new Move(square, "" + c + (char) (row + 1)));

            originalRow = !originalRow;
        }

        return moves;
    }
}
