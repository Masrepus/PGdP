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
        int diagonalOffset = 1;

        //up + diagonally up
        for (char r = (char) (row + 1); r <= '8'; r++) {
            //up
            Move move = new Move("" + column + row, "" + column + r);
            move.setMoveSequence(0);
            moves.add(move);

            //up and left
            move = new Move("" + column + row, "" + (char) (column - diagonalOffset) + r);
            move.setMoveSequence(1);
            moves.add(move);

            //up and right
            move = new Move("" + column + row, "" + (char) (column + diagonalOffset) + r);
            move.setMoveSequence(2);
            moves.add(move);

            diagonalOffset++;
        }

        //down + diagonally down
        diagonalOffset = 1;

        for (char r = (char) (row - 1); r >= '1'; r--) {
            Move move = new Move("" + column + row, "" + column + r);
            move.setMoveSequence(3);
            moves.add(move);

            //down and left
            move = new Move("" + column + row, "" + (char) (column - diagonalOffset) + r);
            move.setMoveSequence(4);
            moves.add(move);

            //down and right
            move = new Move("" + column + row, "" + (char) (column + diagonalOffset) + r);
            move.setMoveSequence(5);
            moves.add(move);

            diagonalOffset++;
        }

        //right
        for (char c = (char) (column + 1); c <= 'h'; c++) {
            Move move = new Move("" + column + row, "" + c + row);
            move.setMoveSequence(6);
            moves.add(move);
        }

        //left
        for (char c = (char) (column - 1); c >= 'a'; c--) {
            Move move = new Move("" + column + row, "" + c + row);
            move.setMoveSequence(7);
            moves.add(move);
        }

        //we're done
        return moves;
    }

}
