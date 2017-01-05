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
        int moveSequence = 0;

        boolean originalRow, originalColumn;

        //up, alternate columns
        originalColumn = false;

        for (char r = (char) (row + 1); r <= '8'; r++) {
            Move move;
            if (originalColumn) move = new Move(square, "" + column + r);
            else move = new Move(square, "" + (char) (column - 1) + r);

            //now tell the move that it is part of this sequence of multiple moves, this is important to determine intermediate fields
            move.setMoveSequence(moveSequence);

            moves.add(move);

            originalColumn = !originalColumn;
        }

        //down
        moveSequence++;
        originalColumn = false;

        for (char r = (char) (row - 1); r >= '1'; r--) {
            Move move;
            if (originalColumn) move = new Move(square, "" + column + r);
            else move = new Move(square, "" + (char) (column + 1) + r);

            //set move sequence
            move.setMoveSequence(moveSequence);

            moves.add(move);

            originalColumn = !originalColumn;
        }

        //left
        moveSequence++;
        originalRow = false;

        for (char c = (char) (column - 1); c >= 'a'; c--) {
            Move move;
            if (originalRow) move = new Move(square, "" + c + row);
            else move = new Move(square, "" + c + (char) (row - 1));

            //set move sequence
            move.setMoveSequence(moveSequence);

            moves.add(move);

            originalRow = !originalRow;
        }

        //right
        moveSequence++;
        originalRow = false;

        for (char c = (char) (column + 1); c <= 'h'; c++) {
            Move move;
            if (originalRow) move = new Move(square, "" + c + row);
            else move = new Move(square, "" + c + (char) (row + 1));

            //set move sequence
            move.setMoveSequence(moveSequence);

            moves.add(move);

            originalRow = !originalRow;
        }

        return moves;
    }
}
