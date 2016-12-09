package com.samuel.pgdp.blatt7;

import com.samuel.pgdp.MJAutoscreen;

public class DameSpiel extends MJAutoscreen {

    private int nrRows, nrColumns; // Board dimensions
    private boolean[][] board;     // true = queen, false = empty
    private boolean whiteToMove;   // Whose turn it is
    private String white, black;   // Players' names

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


    /**
     * Gibt das Spielbrett aus.
     */
    private void printBoard() {
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
     */
    private void initBoard() {
        board = new boolean[nrColumns][nrRows];
    }


    /**
     * Ermittelt die Groesse des Spielbretts gemaess den Spielregeln.
     * Das Ergebnis der Abfrage wird in den Attributen nrRows und nrColumns abgelegt.
     */
    private void determineBoardSize() {
        //ask for board dimensions, white can choose any width from 5 to 6
        nrColumns = readInt("Player " + white + ", please enter the desired board width. (5 to 8)");
        while (nrColumns < 5 || nrColumns > 8) {
            write(nrColumns + " is not a valid input!");
            nrColumns = readInt("Player " + white + ", please enter the desired board width.");
        }

        nrRows = readInt("Player " + black + ", please enter the desired board length. (" + (nrColumns-1) + " to " + (nrColumns+1) + ")");
        while (nrRows < (nrColumns-1) || nrRows > (nrColumns+1)) {
            write(nrRows + " is not a valid input!");
            nrRows = readInt("Player " + black + ", please enter the desired board width.");
        }
    }


    /**
     * Ermittelt, wer anfaengt zu ziehen.
     * Das Ergebnis der Abfrage wird im Attribut whiteToMove abgelegt.
     */
    private void determineFirstPlayer() {
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
    private void applyMove(int move) {
        //TODO
    }


    /**
     * Startet die Hauptschleife des Spiels
     * mit der Abfrage nach Zuegen.
     * Die Schleife wird durch Eingabe von -1 beendet.
     */
    private void mainLoop() {
        int input = 0;
        int maxInput = (10*nrColumns + nrRows);
        String currPlayer;

        while (input != -1) {
            //get the current player
            currPlayer = whiteToMove ? white : black;

            //ask currPlayer where he would like to place a queen
            input = readInt("Player " + currPlayer + ", where would you like to place a queen?\n" +
                    "Input format: XY, where X = column number, Y = row number (as written next to the board)\n" +
                    "Input range: 0 to " + maxInput);
            while (input <= 0 || input > maxInput) {
                write(input + " is not a valid input!");
                input = readInt("Player " + currPlayer + ", where would you like to place a queen?\n" +
                        "Input format: XY, where X = column number, Y = row number (as written next to the board)\n" +
                        "Input range: 0 to " + maxInput);
            }

            //input is legal, now check whether the player can place a queen here without another queen beating this one
            while(!movePossible(input)) {
                write("It is not possible to place a queen on " + input + ", it will be beaten!");
                input = readInt("Player " + currPlayer + ", where would you like to place a queen?\n" +
                        "Input format: XY, where X = column number, Y = row number (as written next to the board)\n" +
                        "Input range: 0 to " + maxInput);
            }
        }
    }

    private boolean movePossible(int input) {
        int row = input % 10 - 1;
        int column = input / 10 - 1;

        //first check if the column is empty
        for (int i = 0; i < board.length; i++) {
            if (board[column][i]) return false;
        }

        //now check if the row is empty
        for (int i = 0; i < board[column].length; i++) {
            if (board[i][row]) return false;
        }

        //nothing found, so now check diagonal, first go right and upwards
        int tempRow = row;
        int tempColumn = column;

        while(tempRow < nrRows + 1 && tempColumn < nrColumns + 1) {
            //one up, one to the right
            tempColumn++;
            tempRow++;
            if (board[tempColumn][tempRow]) return false;
        }

        //now up and left
        while(tempRow < nrRows + 1 && tempColumn > 0) {
            //one up, one to the right
            tempColumn--;
            tempRow++;
            if (board[tempColumn][tempRow]) return false;
        }

        //now down and right
        while(tempRow > 0 && tempColumn < nrColumns + 1) {
            //one up, one to the right
            tempColumn++;
            tempRow--;
            if (board[tempColumn][tempRow]) return false;
        }

        //now down and left
        while(tempRow > 0 && tempColumn > 0) {
            //one up, one to the right
            tempColumn--;
            tempRow--;
            if (board[tempColumn][tempRow]) return false;
        }

        //nothing found, move is ok
        return true;
    }


    /**
     * Informiert die Benutzerin ueber den Ausgang des Spiels.
     * Speziell: Wer hat gewonnen (Weiss oder Schwarz)?
     */
    private void reportWinner() {
        //TODO
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


    public static void main(String[] args) {
        DameSpiel ds = new DameSpiel("Weiß", "Schwarz");
        ds.startGame();
    }

}
