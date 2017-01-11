package com.samuel.pgdp.game;

/**
 * Oberklasse fuer Tiere.
 */
public class Animal {

    // Attribute fuer den allen Tieren gemeinen Teil des Tierzustands
    public boolean female; // Weibchen?
    public boolean alive;  // Lebendig?
    public String square;  // Auf welchem Feld? (genau zwei Zeichen, z. B. "e4")
    public Position position; // Auf welchem Spielbrett?


    /**
     * Dem Konstruktor wird das Geschlecht des Tiers uebergeben.
     *
     */
    public Animal(boolean female) {
        this.female = female;
        alive = true;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getSquare() {
        return square;
    }

    /**
     * Ermittelt die moeglichen Zuege gemaess den Spielregeln,
     * die das Tier von seinem Feld aus in der aktuellen Position
     * ausfuehren kann.
     *
     * Muss von jeder einzelnen Tierklasse ueberschrieben werden.
     */
    public Move[] possibleMoves(){
        //first get the moves the animal can do (without taking into account any animals in its way etc)
        List<Move> rawMoves = getRawPossibleMoves();

        //now check which of these moves are legal
        List<Move> legalMoves = new List<>();
        Move curr;
        for (int i = 0; i < rawMoves.length(); i++) {
            curr = rawMoves.get(i);
            if (isMoveLegal(curr.getTo())) legalMoves.add(curr);
        }

        //now we have the legal moves, convert it to an array
        Move[] moves = new Move[legalMoves.length()];
        for (int i = 0; i < legalMoves.length(); i++) {
            moves[i] = legalMoves.get(i);
        }

        return moves;
    }


    /**
     * Wird aufgerufen nach jeder Spielrunde
     * (quasi am Ende vom Tag - jede Spielrunde zaehlt als ein Tag).
     *
     *  Muss in jeder einzelnen Tierklasse ueberschrieben sein!
     */
    public void sunset(){
        // Methode (und Klasse Animal) sollten eigentlich als abstract deklariert sein.
        // Kommt spaeter in der Vorlesung noch dran...
        // Zum Verstaendnis reicht es, dass diese Methode ueberschrieben werden muss.
        // (Die folgende Zeile wird dann auch nie ausgefuehrt.)
        throw new RuntimeException("Method sunset should have been overridden");
    }

    /**
     * Wrapper method for {@link Position#kill(String)} that also sets {@link #alive} to false. This would not be necessary as the Animal object is being removed but it is required by the game's specifications
     */
    public void kill() {
        alive = false;
        position.kill(square);
    }

    /**
     * Get a String representation of this animal's gender
     *
     * @return "female" if the animal is female, "male" otherwise
     */
    public String getGender() {
        return female ? "female" : "male";
    }

    /**
     * Checks whether this animal can legally move to a given destination. The caller has to check whether the current player is allowed to move this animal.
     * @param destination the desired destination
     * @return true if the move is legal, false otherwise
     */
    public boolean isMoveLegal(String destination) {
        //check if there is another animal at our destination
        Animal targetAnimal = position.getAnimal(destination);

        //check if another animal has already chosen the same destination or has the same starting point (one animal can't move twice in one round
        List<Move> currentMoves = position.getGame().getCurrentMoves();
        if (currentMoves != null) {
            for (int i = 0; i < currentMoves.length(); i++) {
                if (currentMoves.get(i).getTo().equals(destination)) return false;
                if (currentMoves.get(i).getFrom().equals(square)) return false;
            }
        }

        if (targetAnimal != null) {
            //we have an animal there, so we can only move there if we are a predator and the target is a vegetarian, AND if the target is an enemy
            if (targetAnimal.getGender().equals(getGender())) return false;
            if (this instanceof Vegetarian || targetAnimal instanceof Predator) return false;
        }

        //destination field is either clear or we can eat the target
        //now check whether this animal can actually reach this square
        List<Move> possibleMoves = getRawPossibleMoves();
        for (int i = 0; i < possibleMoves.length(); i++) {
            //if we can reach this square, check if the path is free of animals
            if (possibleMoves.get(i).getTo().equals(destination))
                return checkIntermediateFields(possibleMoves, possibleMoves.get(i));
        }

        //doesn't seem like this move is legal
        return false;
    }

    /**
     * Checks if the animal can reach the desired destination without any other animals in its way
     * @param possibleMoves A list of possible moves for this animal, e.g. as it can be obtained by {@link #getRawPossibleMoves()}. Contained moves have to be ordered by numbered move sequences (a move sequence means all moves in the same path)
     * @param move the move that has to be evaluated for empty intermediate fields
     * @return true if the path to move.getTo() is empty
     */
    protected boolean checkIntermediateFields(List<Move> possibleMoves, Move move) {
        //we have to work on a copy of this list because we will remove items
        List<Move> intermediates = possibleMoves.duplicate();

        //first find out which move sequence we have to check
        int moveSequence = move.getMoveSequence();

        //now eliminate all moves that do not belong to this sequence
        for (int i = intermediates.length() - 1; i >= 0; i--) {
            if (intermediates.get(i).getMoveSequence() != moveSequence) intermediates.remove(i);
        }

        //now we only have the moves for the correct move sequence, so we have to eliminate all moves that would come after this move's square
        //moves in possibleMoves are sorted ascending by destination square, so moves after the desired one are later in the list
        int moveId = intermediates.find(move);

        //remove the desired move and all moves afterward
        for (int i = intermediates.length() - 1; i >= moveId; i--) {
            intermediates.remove(i);
        }

        //now we have all intermediate fields, check if they are empty
        for (int i = 0; i < intermediates.length(); i++) {
            if (position.getAnimal(intermediates.get(i).getTo()) != null) return false;
        }

        //no animals could be found in this animal's path
        return true;
    }

    /**
     * Finds all moves that are technically possible for this animal. Doesn't take into account the legality of any moves that point to an occupied field. The caller has to handlle this himself. Moves that have an off-board destination are dismissed by this method
     *
     * @return a {@link List} of moves that are possible for this animal
     */
    protected List<Move> getRawPossibleMoves() {
        //has to be overridden by child classes
        return new List<>();
    }

    /**
     * Prints a graphical representation of all possible moves for this animal. Moves are displayed in the same way as the board. The animal's current position is marked by an X, possible destinations differently, see the description for sequenceNumbers
     * @param sequenceNumbers if true, possible destinations are displayed by the number of their move sequence, otherwise they are displayed as checkmarks (✓)
     */
    public void printPossibleMoves(boolean sequenceNumbers) {
        Move[] moves = possibleMoves();

        System.out.println("   a b c d e f g h");
        for (char r = '8'; r >= '1'; r--) {
            System.out.print(r + " ");
            for (char c = 'a'; c <= 'h'; c++) {
                boolean used = false;
                for (int i = 0; i < moves.length; i++) {
                    if (moves[i].getTo().equals("" + c + r)) {
                        if (sequenceNumbers) System.out.print(" " + moves[i].getMoveSequence());
                        else System.out.print(" ✓");
                        used = true;
                        break;
                    }
                }
                if (square.equals("" + c + r)) System.out.print(" X");
                else if (!used) System.out.print(" -");

            }
            System.out.print(" " + r + "\n");
        }

        System.out.println("   a b c d e f g h");
    }

    /**
     * Adds a given move to a list of moves
     * @param moves the list the move should be added to
     * @param columnOffset the offset to {@link #square} in columns
     * @param rowOffset the offset to {@link #square} in rows
     */
    protected void addMove(List<Move> moves, int columnOffset, int rowOffset) {
        char column = square.charAt(0);
        char row = square.charAt(1);
        moves.add(new Move("" + column + row, "" + (char) (column + columnOffset) + (char) (row + rowOffset)));
    }

    /**
     * Works in the same way as {@link #addMove(List, int, int)}, but also sets the new move's moveSequence
     * @param moves see {@link #addMove(List, int, int)}
     * @param columnOffset see {@link #addMove(List, int, int)}
     * @param rowOffset see {@link #addMove(List, int, int)}
     * @param moveSequence the new move's moveSequence
     */
    protected void addMove(List<Move> moves, int columnOffset, int rowOffset, int moveSequence) {
        char column = square.charAt(0);
        char row = square.charAt(1);
        Move move = new Move("" + column + row, "" + (char) (column + columnOffset) + (char) (row + rowOffset));
        move.setMoveSequence(moveSequence);
        moves.add(move);
    }

    /**
     * Removes moves from a given list of moves whose destinations would not be on the board anymore
     * @param tmp the list of moves to be cleaned up
     * @return tmp without offscreen moves
     */
    protected List<Move> removeOffScreenMoves(List<Move> tmp) {
        //now eliminate all moves whose destinations are not on the board
        List<Move> moves = new List<>();
        for (int i = 0; i < tmp.length(); i++) {
            Move move = tmp.get(i);
            char column = move.getTo().charAt(0);
            char row = move.getTo().charAt(1);
            if (Game.isColumn(column) && Game.isRow(row)) moves.add(move);
        }

        return moves;
    }

    /**
     * Same as {@link #removeOffScreenMoves(List)} but if it detects an offscreen move, all moves that belong to the same move sequence are deleted, too. This is especially important for {@link Snake}
     * @param tmp the list of moves to be cleaned up
     * @return tmp without move sequences that contain offscreen moves
     */
    protected List<Move> removeOffScreenMoveSequences(List<Move> tmp) {
        //now eliminate all moves whose destinations are not on the board
        List<Move> moves = new List<>();

        //if offscreen moves are found, remove all moves of the same sequence coming afterwards
        List<Integer> offScreenSequences = new List<>();
        for (int i = 0; i < tmp.length(); i++) {
            Move move = tmp.get(i);

            //check if this sequences is already marked for deletion
            if (offScreenSequences.find(move.getMoveSequence()) == -1) {
                //sequence not marked yet
                char column = move.getTo().charAt(0);
                char row = move.getTo().charAt(1);
                if (!Game.isColumn(column) || !Game.isRow(row)) {
                    //add this sequence to the offscreen sequences
                    offScreenSequences.add(move.getMoveSequence());
                } else moves.add(move);
            } //sequence found, move will not be added to moves
        }

        return moves;
    }
}
