package com.samuel.pgdp.game;

/**
 * Die Klasse Game fuehrt die Benutzerinteraktion durch.
 *
 */

public class Game {

    private Position pos;


    /**
     * Startet ein neues Spiel.
     * Der Benutzer wird ueber das Spielgeschehen informiert.
     *
     * Dazu gehoert auch die Information, wie lange die
     * einzelnen Raubtiere noch ohne Essen auskommen koennen.
     * Diese Information soll auf Anfrage oder immer angezeigt werden.
     *
     * Es soll ausserdem eine Moeglichkeit geben, sich alle Zuege
     * anzeigen zu lassen, die in der Spielsituation moeglich sind.
     *
     * Bei fehlerhaften Eingaben wird die Eingabe natuerlich wiederholt.
     *
     * Der Parameter spezifiziert, wer das Spiel beginnen darf.
     */
    public void startGame(boolean ladiesFirst){
        pos = new Position();
        pos.reset(ladiesFirst ? 'W' : 'M');

        //start game loop
        while (!gameOver()) {
            //first print the board and animal infos
            System.out.println(pos);
            pos.printDaysRemaining();

            //now ask for the next move
            System.out.println();
            nextMove();

            //it's the next player's turn
            //first print the board and animal infos
            System.out.println(pos);
            pos.printDaysRemaining();

            //now ask for the next move
            System.out.println();
            nextMove();

            //both players have played, invoke sunset
            pos.doSunset();
        }
    }

    /**
     * Handles one player's moves: You can do up to 4 moves per round, but not more than 3 vegetarians and 1 predator. Handles input format correctness and semantical legality regarding the animal to move and the desired target square. If the player has submitted 4 correct moves or aborted the adding process, the chosen moves are passed on to {@link Position#applyMoves(Move[])}
     */
    private void nextMove() {
        Move[] moves = new Move[4];
        int i = 0;
        int vegRemaining, predRemaining;
        //3 vegetarians and 1 predator can be moved in one round
        vegRemaining = 3;
        predRemaining = 1;

        //the player is allowed to do up to 4 moves per round
        outerLoop:
        while (i < 4) {
            Animal targetAnimal = null;
            String move;
            do {
                //ask which move we should do
                move = IO.readString("\nPlease choose your next move (format: XYAB where X = start square column, Y = start square row, A = destination square column, B = destination square row. Example: a2a3) or type x to stop adding moves\n");
                while (!formatIsLegal(move)) {
                    System.out.println("The format of " + move + " is not legal!");
                    move = IO.readString("\nPlease choose your next move (format: XYAB where X = start square column, Y = start square row, A = destination square column, B = destination square row. Example: a2a3) or type x to stop adding moves\n");
                }

                //check if the player doesn't want to add any more moves
                if (move.equals("x") || move.equals("X")) break outerLoop; //stop the whole process of adding moves

                //the move's format is legal, now check if the player is allowed to make this move with the selected animal
                targetAnimal = pos.getAnimal("" + move.charAt(0) + move.charAt(1));

                //check if this animal exists
                if (targetAnimal == null)
                    System.out.println("There is no animal at " + move.charAt(0) + move.charAt(1) + ", choose your move again!");
            } while (targetAnimal == null);

            //check if this animal is allowed to move (only 3 vegetarians and 1 predator per round!)
            if (targetAnimal instanceof Vegetarian && vegRemaining > 0 || targetAnimal instanceof Predator && predRemaining > 0) {
                //animal is allowed to move, now check if it is allowed to move to the desired destination
                if (targetAnimal.isMoveLegal("" + move.charAt(2) + move.charAt(3))) {
                    //move is ok, add it to the current moves
                    moves[i] = new Move(move);

                    //increase the move counter, decrease the correct "remaining" counter and tell the player how many moves he can still add
                    i++;
                    if (targetAnimal instanceof Vegetarian) vegRemaining--;
                    else predRemaining--;
                    System.out.println("Move " + move + " has been added to this round's moves. You can now add up to " + (4 - i) + " more moves:\n" +
                            vegRemaining + " vegetarians, " + predRemaining + " predators.\n");
                }
            } else {
                //tell the player that he can not move any more vegetarians or predators if the corresponding counter is 0
                System.out.println("Your chosen animal is a " + (targetAnimal instanceof Vegetarian ? "vegetarian" : "predator") + ", but you are not allowed to move any more animals of this kind! Choose again!");
            }
        }

        //player is done adding moves, now execute them
        pos.applyMoves(moves);
    }

    /**
     * Checks whether a String input matches the ColumnRowColumnRow scheme for moves
     *
     * @param move the String to test
     * @return true if {@code move} matches the scheme or if the input was x or X
     */
    private boolean formatIsLegal(String move) {
        //check if the input string's format is correct
        if (move.equals("x") || move.equals("X")) return true;
        else
            return move.length() == 4 && isColumn(move.charAt(0)) && isRow(move.charAt(1)) && isColumn(move.charAt(2)) && isRow(move.charAt(3));
    }

    /**
     * Checks whether a given char refers to a column
     * @param c the char to test
     * @return true if {@code c} is between 'a' and 'h'
     */
    private boolean isColumn(char c) {
        return (c <= 'h' && c >= 'a');
    }

    /**
     * Checks whether a given char refers to a column
     * @param c the char to test
     * @return true if {@code c} is between 1 and 8
     */
    private boolean isRow(char c) {
        return (c <= '8' && c >= '1');
    }

    /**
     * Wrapper method for {@link Position#theWinner()}
     * @return true if there is a winner, i.e. {@link Position#theWinner()} != 'X'
     */
    private boolean gameOver() {
        return pos.theWinner() != 'X';
    }
}
