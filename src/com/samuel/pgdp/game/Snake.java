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

    /**
     * {@inheritDoc}
     * default for snake: 9
     */
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
        List<Move> moves = new List<>();
        char column = square.charAt(0);
        char row = square.charAt(1);
        int moveSequence = 0;

        boolean originalRow, originalColumn;

        //up, alternate columns
        originalColumn = false;

        for (int rowOffset = 1; (char) (row + rowOffset) <= '8'; rowOffset++) {
            if (originalColumn) addMove(moves, 0, rowOffset, moveSequence);
            else addMove(moves, -1, rowOffset, moveSequence);

            originalColumn = !originalColumn;
        }

        //down
        moveSequence++;
        originalColumn = false;

        for (int rowOffset = -1; (char) (row + rowOffset) >= '1'; rowOffset--) {
            if (originalColumn) addMove(moves, 0, rowOffset, moveSequence);
            else addMove(moves, 1, rowOffset, moveSequence);

            originalColumn = !originalColumn;
        }

        //left
        moveSequence++;
        originalRow = false;

        for (int columnOffset = -1; (char) (column + columnOffset) >= 'a'; columnOffset--) {
            if (originalRow) addMove(moves, columnOffset, 0, moveSequence);
            else addMove(moves, columnOffset, -1, moveSequence);

            originalRow = !originalRow;
        }

        //right
        moveSequence++;
        originalRow = false;

        for (int columnOffset = 1; (char) (column + columnOffset) <= 'h'; columnOffset++) {
            if (originalRow) addMove(moves, columnOffset, 0, moveSequence);
            else addMove(moves, columnOffset, 1, moveSequence);

            originalRow = !originalRow;
        }

        return removeOffScreenMoveSequences(moves);
    }
}
