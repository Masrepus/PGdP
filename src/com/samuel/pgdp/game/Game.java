package com.samuel.pgdp.game;

import com.samuel.pgdp.MutableList;

/**
 * Die Klasse Game fuehrt die Benutzerinteraktion durch.
 */

public class Game {

    private Position pos;
    private MutableList<Move> currentMoves;


    /**
     * Startet ein neues Spiel.
     * Der Benutzer wird ueber das Spielgeschehen informiert.
     * <p>
     * Dazu gehoert auch die Information, wie lange die
     * einzelnen Raubtiere noch ohne Essen auskommen koennen.
     * Diese Information soll auf Anfrage oder immer angezeigt werden.
     * <p>
     * Es soll ausserdem eine Moeglichkeit geben, sich alle Zuege
     * anzeigen zu lassen, die in der Spielsituation moeglich sind.
     * <p>
     * Bei fehlerhaften Eingaben wird die Eingabe natuerlich wiederholt.
     * <p>
     * Der Parameter spezifiziert, wer das Spiel beginnen darf.
     */
    public void startGame(boolean ladiesFirst) {
        pos = new Position();
        pos.setGame(this);
        pos.reset(ladiesFirst ? 'W' : 'M');

        //start game loop
        while (!gameOver()) {
            //first print the board and animal infos
            System.out.println(pos);
            pos.printDaysRemaining();

            //now ask for the next move
            System.out.println();
            nextMove();

            //maybe it's already game-over, check this and end the game if so
            if (gameOver()) break;

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
     * Handles one player's moves: You can do up to 4 moves per round, but not more than 3 vegetarians and 1 predator. Handles input format correctness and semantical legality regarding the animal to move and the desired target square. If the player has submitted 4 correct moves or aborted the adding process, the chosen moves are passed on to {@link Position#executeMoves(Move[])}
     */
    private void nextMove() {
        currentMoves = new MutableList<>();
        int i = 0;
        int vegRemaining, predRemaining;
        //3 vegetarians and 1 predator can be moved in one round
        vegRemaining = 3;
        predRemaining = 1;

        //the player is allowed to do up to 4 moves per round
        while (i < 4) {
            Animal movingAnimal;
            String move;

            //ask which move we should do
            move = IO.readString("\nPlease choose your next move (format: XYAB where X = start square column, Y = start square row, A = destination square column, B = destination square row. Example: a2a3) or type x to stop adding moves\n" +
                    "Typing the word 'moves' shows a list of all possible moves\n" +
                    "The current board situation can be shown with 'board'\n");
            while (!formatIsLegal(move)) {
                System.out.println("The format of " + move + " is not legal!");
                move = IO.readString("\nPlease choose your next move (format: XYAB where X = start square column, Y = start square row, A = destination square column, B = destination square row. Example: a2a3) or type x to stop adding moves\n" +
                        "Typing the word 'moves' shows a list of all possible moves\n" +
                        "The current board situation can be shown with 'board'\n");
            }

            //check if the player wants to get a list of possible moves
            if (move.equals("moves")) {
                pos.printPossibleMoves();
                continue;
            }

            //check if the player wants an overview of the board
            if (move.equals("board")) {
                System.out.println(pos);
                pos.printDaysRemaining();
            }

            //check if the player doesn't want to add any more moves
            if (move.equals("x") || move.equals("X")) break; //stop the whole process of adding moves

            //the move's format is legal, now check if the player is allowed to make this move with the selected animal
            movingAnimal = pos.getAnimal("" + move.charAt(0) + move.charAt(1));

            //check if this animal exists
            if (movingAnimal == null) {
                System.out.println("There is no animal at " + move.charAt(0) + move.charAt(1) + ", choose your move again!");
                continue;
            }

            //check if this animal belongs to the current player
            if (!movingAnimal.getGender().equals(pos.getNext() == 'W' ? "female" : "male")) {
                System.out.println("This animal is not yours! Choose a different one!");
                continue;
            }

            //check if this animal is allowed to move (only 3 vegetarians and 1 predator per round!)
            if (movingAnimal instanceof Vegetarian && vegRemaining > 0 || movingAnimal instanceof Predator && predRemaining > 0) {
                //animal is allowed to move, now check if it is allowed to move to the desired destination
                if (movingAnimal.isMoveLegal("" + move.charAt(2) + move.charAt(3))) {
                    //move is ok, add it to the current moves
                    currentMoves.add(new Move(move));

                    //increase the move counter, decrease the correct "remaining" counter and tell the player how many moves he can still add
                    i++;
                    if (movingAnimal instanceof Vegetarian) vegRemaining--;
                    else predRemaining--;
                    System.out.println("Move " + move + " has been added to this round's moves. You can now add up to " + (4 - i) + " more moves:\n" +
                            vegRemaining + " vegetarians, " + predRemaining + " predators.\n");
                } else {
                    //this move is not legal
                    System.out.println("You can't move animal " + movingAnimal.getSquare() + " to " + move.charAt(2) + move.charAt(3) + ", Choose again!");
                }
            } else {
                //tell the player that he can not move any more vegetarians or predators if the corresponding counter is 0
                System.out.println("Your chosen animal is a " + (movingAnimal instanceof Vegetarian ? "vegetarian" : "predator") + ", but you are not allowed to move any more animals of this kind! Choose again!");
            }
        }

        //now convert the list to an array
        Move[] moveArray = new Move[currentMoves.length()];
        for (int j = 0; j < currentMoves.length(); j++) {
            moveArray[j] = currentMoves.get(j);
        }

        //player is done adding moves, now execute them
        pos.executeMoves(moveArray);
    }

    /**
     * Checks whether a String input matches the ColumnRowColumnRow scheme for moves
     *
     * @param move the String to test
     * @return true if {@code move} matches the scheme or if the input was x or X
     */
    public static boolean formatIsLegal(String move) {
        //check if the input string's format is correct
        return move.equals("x") || move.equals("X") || move.equals("moves") || move.equals("board") || move.length() == 4 && isColumn(move.charAt(0)) && isRow(move.charAt(1)) && isColumn(move.charAt(2)) && isRow(move.charAt(3));
    }

    /**
     * Checks whether a given char refers to a column
     *
     * @param c the char to test
     * @return true if {@code c} is between 'a' and 'h'
     */
    public static boolean isColumn(char c) {
        return (c <= 'h' && c >= 'a');
    }

    /**
     * Checks whether a given char refers to a column
     *
     * @param c the char to test
     * @return true if {@code c} is between 1 and 8
     */
    public static boolean isRow(char c) {
        return (c <= '8' && c >= '1');
    }

    /**
     * Wrapper method for {@link Position#theWinner()}, additionally prints the game over message
     *
     * @return true if there is a winner, i.e. {@link Position#theWinner()} != 'X'
     */
    private boolean gameOver() {
        char winner = pos.theWinner();
        if (winner != 'X') {
            System.out.println(pos);
            if (winner != 'N') System.out.println("The game is over! Player " + winner + " is the winner!");
            else System.out.println("The game is over! It's a tie, everybody is a winner ;P");
            return true;
        } else return false;
    }

    /**
     * Getter method for {@link #currentMoves}, where the moves are being stored that have already been entered for this round
     *
     * @return {@link #currentMoves}
     */
    public MutableList<Move> getCurrentMoves() {
        return currentMoves;
    }
}
