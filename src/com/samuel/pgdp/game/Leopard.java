package com.samuel.pgdp.game;

public class Leopard extends Predator {

    // Ein Leopard kann nur 5 Tage bzw. Spielrunden ohne Essen auskommen.
    // Die Deklaration darf entfernt (und der Wert z. B. direkt im Code
    // verwendet) werden.


    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Leopard(boolean female) {
        super(female);
        daysRemaining = 5;
    }

    @Override
    protected void resetDaysRemaining() {
        daysRemaining = 5;
    }

    @Override
    public Move[] possibleMoves() {
        return super.possibleMoves();
    }

    @Override
    public String toString(){
        return this.female
                ? (Globals.darkSquare(this.square) ? Globals.ts_female_leopard_dark : Globals.ts_female_leopard_light)
                : (Globals.darkSquare(this.square) ? Globals.ts_male_leopard_dark : Globals.ts_male_leopard_light);
    }

    @Override
    protected List<Move> getRawPossibleMoves() {
        List<Move> moves = new List<>();
        char column = square.charAt(0);
        char row = square.charAt(1);

        //up + diagonally up
        for (int rowOffset = 1, diagonalOffset = 1; (char) (row + rowOffset) <= '8'; rowOffset++, diagonalOffset++) {
            //up
            addMove(moves, 0, rowOffset, 0);

            //up and left
            addMove(moves, -diagonalOffset, rowOffset, 1);

            //up and right
            addMove(moves, diagonalOffset, rowOffset, 2);
        }

        //down + diagonally down
        for (int rowOffset = -1, diagonalOffset = 1; (char) (row + rowOffset) >= '1'; rowOffset--, diagonalOffset++) {
            //down
            addMove(moves, 0, rowOffset, 3);

            //down and left
            addMove(moves, -diagonalOffset, rowOffset, 4);

            //down and right
            addMove(moves, diagonalOffset, rowOffset, 5);
        }

        //right
        for (int columnOffset = 1; (char) (column + columnOffset) <= 'h'; columnOffset++) {
            addMove(moves, columnOffset, 0, 6);
        }

        //left
        for (int columnOffset = -1; (char) (column + columnOffset) >= 'a'; columnOffset--) {
            addMove(moves, columnOffset, 0, 7);
        }

        //we're done
        return removeOffScreenMoveSequences(moves);
    }

}
