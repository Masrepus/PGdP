package com.samuel.pgdp.blatt7;

import com.samuel.pgdp.MiniJava;

public class DameSpiel extends MiniJava {

    public int nrRows, nrColumns; // Board dimensions
    public boolean[][] board, possibleMoves;     // true = queen, false = empty
    public boolean whiteToMove;   // Whose turn it is
    public String white, black;   // Players' names
    public String winnerMessage;

    /**
     * Der Konstruktor registriert Spielernamen fuer Weiss und Schwarz.
     *
     * @param white Name des als 'Weiss' bezeichneten Spielers
     * @param black Name des als 'Schwarz' bezeichneten Spielers
     */
    public DameSpiel(String white, String black) {
        this.white = white;
        this.black = black;
    }

    public static void main(String[] args) {
        DameSpiel ds = new DameSpiel("Weiß", "Schwarz");
        ds.startGame();
    }

    /**
     * Gibt das Spielbrett aus.
     */
    public void printBoard() {
        for (int j = board[0].length - 1; j >= 0; j--) {
            System.out.print("\n " + (1 + j));
            for (int i = 0; i < board.length; i++) {
                System.out.print(board[i][j] ? " X" : " -");
            }
        }
        System.out.print("\n  ");
        for (int i = 1; i <= board.length; i++) {
            System.out.print(" " + i);
        }
        System.out.println("\n" + (whiteToMove ? white : black) + " ist am Zug.");
    }

    /**
     * Initialisiert das Spielbrett ueberall mit false.
     * Dazu wird (ggf. neuer) Speicher allokiert.
     * Zusätzlich wird der Scanvorgang eingeleitet, der nach möglichen Zügen sucht
     */
    public void initBoard() {
        board = new boolean[nrColumns][nrRows];
        possibleMoves = new boolean[nrColumns][nrRows];
        //now init possibleMoves
        scanPossibleMoves();
    }

    /**
     * Ermittelt die Groesse des Spielbretts gemaess den Spielregeln.
     * Das Ergebnis der Abfrage wird in den Attributen nrRows und nrColumns abgelegt.
     */
    public void determineBoardSize() {
        //ask for board dimensions, white can choose any width from 5 to 6
        nrColumns = readInt("Player " + white + ", please enter the desired board width. (5 to 8)");
        while (nrColumns < 5 || nrColumns > 8) {
            write(nrColumns + " is not a valid input!");
            nrColumns = readInt("Player " + white + ", please enter the desired board width.");
        }

        nrRows = readInt("Player " + black + ", please enter the desired board length. (" + (nrColumns - 1) + " to " + (nrColumns + 1) + ")");
        while (nrRows < (nrColumns - 1) || nrRows > (nrColumns + 1)) {
            write(nrRows + " is not a valid input!");
            nrRows = readInt("Player " + black + ", please enter the desired board width.");
        }
    }

    /**
     * Ermittelt, wer anfaengt zu ziehen.
     * Das Ergebnis der Abfrage wird im Attribut whiteToMove abgelegt.
     */
    public void determineFirstPlayer() {
        //ask white who should start
        int startWhite = readInt("Player " + white + ", do you want to begin (1) or do you want " + black + " to start (0) ?");
        while (startWhite != 0 && startWhite != 1) {
            write(startWhite + " is not a valid input!");
            startWhite = readInt("Player " + white + ", do you want to begin (1) or do you want " + black + " to start (0) ?");
        }

        whiteToMove = (startWhite == 1);
    }

    /**
     * Fuehrt den Zug aus.
     *
     * @param move der auszufuehrende Zug!
     */
    public void applyMove(int move) {
        board[move / 10 - 1][move % 10 - 1] = true;
        //update the possibleMoves table
        scanPossibleMoves();
        printBoard();
    }

    /**
     * Startet die Hauptschleife des Spiels
     * mit der Abfrage nach Zuegen.
     * Die Schleife wird durch Eingabe von -1 beendet.
     */
    public void mainLoop() {
        int input = 0;
        String currPlayer;

        while (input != -1) {
            //get the current player
            currPlayer = whiteToMove ? white : black;

            //first check if there are any possible moves
            if (arePossibleMoves()) {
                //ask currPlayer where he would like to place a queen
                input = readInt("Player " + currPlayer + ", where would you like to place a queen?\n" +
                        "Input format: XY, where X = column number, Y = row number (as written next to the board)\n" +
                        "Input range: X from 1 to " + nrColumns + ", Y from 1 to " + nrRows);
                while (input != -1 && (input < 11 || input / 10 > nrColumns || input % 10 > nrRows)) {
                    write(input + " is not a valid input!");
                    input = readInt("Player " + currPlayer + ", where would you like to place a queen?\n" +
                            "Input format: XY, where X = column number, Y = row number (as written next to the board)\n" +
                            "Input range: X from 1 to " + nrColumns + ", Y from 1 to " + nrRows);
                }

                //check if the player wants to fold
                if (input == -1) {
                    winnerMessage = "Player " + currPlayer + " has given up. Player " + (whiteToMove ? black : white) + " has won!";
                    reportWinner();
                    break;
                }

                //input is legal, now check whether the player can place a queen here without another queen beating this one, or if this position is already occupied
                while (!movePossible(input)) {
                    write("It is not possible to place a queen on " + input + ". This field is full or your queen will be beaten here!");
                    input = readInt("Player " + currPlayer + ", where would you like to place a queen?\n" +
                            "Input format: XY, where X = column number, Y = row number (as written next to the board)\n" +
                            "Input range: X from 1 to " + nrColumns + ", Y from 1 to " + nrRows);
                }

                //move is ok, apply it
                applyMove(input);

                //now it's the next player's turn
                whiteToMove = !whiteToMove;

            } else {
                //no further moves allowed, tell the player that the game has ended
                String winner = (currPlayer.equals(white)) ? black : white;
                winnerMessage = "No more moves are possible! The game has ended, player " + winner + " has won.";
                reportWinner();
                break;
            }
        }
    }

    /**
     * Checks the whole board for possible moves
     * @return true if a queen can be placed on at least one more field on the board
     */
    public boolean arePossibleMoves() {
        //iterate through possibleMoves, if there is no true, there are no possible moves
        for (int i = 0; i < possibleMoves.length; i++) {
            for (int j = 0; j < possibleMoves[i].length; j++) {
                if (possibleMoves[i][j]) return true;
            }
        }

        //nothing found
        return false;
    }

    /**
     * Checks whether this specific move is possible
     * @param input the move to be evaluated. Syntax: XY, X = column number Y = row number
     * @return true if this move is possible, false if the queen would be beaten by another one if placed here
     */
    public boolean movePossible(int input) {
        int columnId = input / 10 - 1;
        int rowId = input % 10 - 1;

        //is this position full already?
        if (board[columnId][rowId]) return false;

        //check if the column is empty
        for (int i = 0; i < nrRows; i++) {
            if (board[columnId][i]) return false;
        }

        //now check if the row is empty
        for (int i = 0; i < nrColumns; i++) {
            if (board[i][rowId]) return false;
        }

        //nothing found, so now check diagonal, first go right and upwards
        int tempRow, tempColumn;
        tempRow = rowId;
        tempColumn = columnId;

        while (tempRow < nrRows - 1 && tempColumn < nrColumns - 1) {
            //one up, one to the right
            tempColumn++;
            tempRow++;
            if (board[tempColumn][tempRow]) return false;
        }

        //reset values
        tempRow = rowId;
        tempColumn = columnId;

        //now up and left
        while (tempRow < nrRows - 1 && tempColumn > 0) {
            //one up, one to the left
            tempColumn--;
            tempRow++;
            if (board[tempColumn][tempRow]) return false;
        }

        tempRow = rowId;
        tempColumn = columnId;

        //now down and right
        while (tempRow > 0 && tempColumn < nrColumns - 1) {
            //one down, one to the right
            tempColumn++;
            tempRow--;
            if (board[tempColumn][tempRow]) return false;
        }

        tempRow = rowId;
        tempColumn = columnId;

        //now down and left
        while (tempRow > 0 && tempColumn > 0) {
            //one up, one to the right
            tempColumn--;
            tempRow--;
            if (board[tempColumn][tempRow]) return false;
        }

        //nothing found, move is ok
        return true;
    }

    /**
     * Saves all possible moves in {@code possibleMoves}
     */
    public void scanPossibleMoves() {
        //check for each position in board whether the player should be allowed to put a queen here
        for (int c = 1; c <= nrColumns; c++) {
            for (int r = 1; r <= nrRows; r++) {
                possibleMoves[c - 1][r - 1] = movePossible(10 * c + r);
            }
        }
    }

    /**
     * An optional helper method that prints the possibleMoves version of the board. Each field where a queen can be placed is marked by a checkmark, all other fields by an x
     */
    public void printCurrPossibleMoves() {
        //print possibleMoves matrix
        for (int j = possibleMoves[0].length - 1; j >= 0; j--) {
            System.out.print("\n " + (1 + j));
            for (int i = 0; i < possibleMoves.length; i++) {
                System.out.print(possibleMoves[i][j] ? " ✓" : " x");
            }
        }
        System.out.print("\n  ");
        for (int i = 1; i <= possibleMoves.length; i++) {
            System.out.print(" " + i);
        }
    }

    /**
     * Informiert die Benutzerin ueber den Ausgang des Spiels.
     * Speziell: Wer hat gewonnen (Weiss oder Schwarz)?
     */
    public void reportWinner() {
        write(winnerMessage);
    }

    /**
     * Startet das Spiel.
     */
    public void startGame() {
        determineBoardSize();
        initBoard();
        determineFirstPlayer();
        printBoard();
        mainLoop();
        reportWinner();
    }

}
