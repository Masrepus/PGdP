package com.samuel.pgdp.blatt5;

import com.samuel.pgdp.MiniJava;

public class Linja extends MiniJava {

    private static int[][] spielfeld = new int[8][6];
    private static int[] numberPieces = new int[]{12,12}; //0 is player -1 and 1 is player 1
    private static int[] notDisplayed = new int[2]; //0 is player -1 and 1 is player 1

    /**
     * initialisiert das Spielfeld
     * Ziellinie fuer Spieler 1 ist Zeile 7
     * Ziellinie fuer Spieler -1 ist Zeile 0
     */
    private static void initSpiel() {
        for (int i = 0; i < spielfeld.length; i++) {
            if (i != 0 && i != spielfeld.length - 1) {
                spielfeld[i] = new int[]{-(12 - i + 1), 0, 0, 0, 0, 6 + i};
            }
            if (i == 0) {
                spielfeld[i] = new int[]{1, 2, 3, 4, 5, 6};
            }
            if (i == spielfeld.length - 1) {
                spielfeld[i] = new int[]{-6, -5, -4, -3, -2, -1};
            }
        }

    }

    /**
     * @return formatiertes aktuelles Spielfeld
     */
    private static String output() {
        String tmp = "Spieler 1 spielt von oben nach unten\n"
                + "Spieler -1 spielt von unten nach oben\n";
        for (int i = 0; i < spielfeld.length; i++) {
            for (int j = 0; j < spielfeld[i].length; j++) {
                tmp = tmp + "\t" + spielfeld[i][j];
            }
            tmp = tmp + "\n";
        }
        return tmp;
    }

    /**
     * @return true, wenn die Eingabe stein im richtigen Wertebereich liegt und
     * zum Spieler gehoert; false, sonst
     */
    private static boolean gueltigeEingabe(int stein, int spieler) {
        //check if stein is negative or positive and if this matches spieler
        if (spieler == -1) {
            return stein < 0 && stein >= -12;
        } else if (spieler == 1) {
            return stein > 0 && stein <= 12;
        } else return false;
    }

    /**
     * @param stein kann Werte -1 bis -12 und 1 bis 12 haben
     * @return gibt x-Koordinate von stein an Position 0 und die y-Koordinaten
     * von stein an Position 1 zurueck; falls stein nicht gefunden, wird {-1,-1}
     * zurueckgegeben
     */
    private static int[] findeStein(int stein) {
        //look for the player's position in the matrix
        for (int i = 0; i < spielfeld.length; i++) {
            for (int j = 0; j < spielfeld[i].length; j++) {
                if (spielfeld[i][j] == stein) return new int[]{i, j};
            }
        }

        //playing piece couldn't be found, return {-1,-1}
        return new int[]{-1, -1};
    }

    /**
     * @param reihe hat Werte 0 bis 7
     * @return Anzahl der Steine in einer Reihe
     */
    private static int steineInReihe(int reihe) {
        //count number of non-empty places in row
        int count = 0;

        for (int i = 0; i < spielfeld[reihe].length; i++) {
            if (spielfeld[reihe][i] != 0) count++;
        }

        return count;
    }

    /**
     * Ueberprueft, ob der Zug zulaessig ist und fuehrt diesen aus, wenn er
     * zulaessig ist.
     *
     * @param vorwaerts == true: Zug erfolgt vorwaerts aus Sicht des
     *                  Spielers/Steins vorwearts == false: Zug erfolgt rueckwaerts aus Sicht des
     *                  Spielers/Steins
     * @return Rueckgabe -1: Zug nicht zulaessig Rueckgabe 0-5: Weite des
     * potentiellen naechsten Zugs (falls Folgezug folgt) Rueckgabe 6: Ziellinie
     * wurde genau getroffen (potentieller Bonuszug)
     */
    private static int setzeZug(int stein, int weite, boolean vorwaerts) {
        //check where stein is and what the destination would be
        int[] currPosition = findeStein(stein);
        if (currPosition[0] == -1 && currPosition[1] == -1) return -1;

        int finishLine = (stein < 0) ? 0 : 7;

        //you also can't move a piece one row forward if it has already crossed the finish line
        if (vorwaerts && currPosition[0] == finishLine) return -1;

        int destRow;
        if (stein < 0) destRow = currPosition[0] - weite;
        else destRow = currPosition[0] + weite;

        //check if destRow is still between 0 and 7 or exactly 7 (potential bonus round)
        if (stein > 0 && destRow > 7 || stein < 0 && destRow < 0) {
            //remove the piece from the game
            spielfeld[currPosition[0]][currPosition[1]] = 0;

            //increase the number of pieces that are not displayed
            if (stein < 0) notDisplayed[0]++;
            else notDisplayed[1]++;

            if (stein < 0) numberPieces[0]--;
            else numberPieces[1]--;
            return 0;
        }
        else if (destRow == finishLine) {
            //remove the piece from the game
            spielfeld[currPosition[0]][currPosition[1]] = 0;

            //increase the number of pieces that are not displayed
            if (stein < 0) notDisplayed[0]++;
            else notDisplayed[1]++;

            if (stein < 0) numberPieces[0]--;
            else numberPieces[1]--;
            return 6;
        }
        else {
            //check if the player is allowed to place his piece here
            int piecesInRow = steineInReihe(destRow);
            if (piecesInRow == 6) return -1;
            else {
                //put the piece in this row
                //look for an empty spot here and put the piece there
                for (int i = 0; i < spielfeld[destRow].length; i++) {
                    if (spielfeld[destRow][i] == 0) {
                        spielfeld[destRow][i] = stein;
                        break;
                    }
                }
                //remove the piece from its original position
                spielfeld[currPosition[0]][currPosition[1]] = 0;
                return piecesInRow;
            }
        }
    }

    /**
     * @return true, falls die Bedingungen des Spielendes erfuellt sind, d.h.
     * alle Steine des einen Spielers sind an den Steinen des gegnerischen Spielers
     * vorbeigezogen
     */
    private static boolean spielende() {
        //iterate through the rows as long as there are only pieces of one color in this row. Meanwhile count the pieces in these rows and stop if we got all pieces that are still in the game
        int onlyPlayer = 0; //this will be set to either 1 or -1 according to the pieces that are found, so if we find piece 2, we set this to 1
        int number1 = 0;
        int numberMinus1 = 0;

        for (int i = 0; i < spielfeld.length; i++) {
            for (int j = 0; j < spielfeld[i].length; j++) {
                if (number1 == numberPieces[1] || numberMinus1 == numberPieces[0]) return true; //we found all pieces of one color without finding one of the other color
                if (spielfeld[i][j] > 0) {
                    if (onlyPlayer == -1) return false;
                    else onlyPlayer = 1;
                    number1++;
                }
                else if (spielfeld[i][j] < 0) {
                    if (onlyPlayer == 1) return false;
                    else onlyPlayer = -1;
                    numberMinus1++;
                }
            }
        }

        //we shouldn't get here
        return false;
    }

    /**
     * zaehlt die Punkte der beiden Spieler und gibt das Ergebnis aus
     */
    private static void zaehlePunkte() {
        //first player -1
        int pointsMinus1 = 0;
        int points1 = 0;

        for (int i = 1; i <= 3; i++) {
            for (int j = 0; j < spielfeld[i].length; j++) {
                //add points and substract any +1 pieces still left here from player1
                if (spielfeld[i][j] < 0) pointsMinus1 += 4 - i;
                else if (spielfeld[i][j] > 0) points1 -= 4 - i;
            }
        }

        //count the pieces that are not being displayed
        pointsMinus1 += 5*notDisplayed[0];

        //now player 1
        for (int i = 6; i >= 4; i--) {
            for (int j = 0; j < spielfeld[i].length; j++) {
                //add points and substract any -1 pieces still left here from playerMinus1
                if (spielfeld[i][j] > 0) points1 += i - 3;
                else if (spielfeld[i][j] < 0) pointsMinus1 -= i - 3;
            }
        }

        //for player 1 also count the pieces that are not being displayed
        points1 += 5*notDisplayed[1];

        //get the winner
        if (points1 == pointsMinus1) write("The game has ended. Tie! " + pointsMinus1 + " (player -1) : " + points1 + " (player 1)");
        else {
            String winner = (points1 > pointsMinus1) ? "Player 1 has won! " : "Player 2 has won! ";
            //output the points
            write("The game has ended. " + winner + pointsMinus1 + " (player -1) : " + points1 + " (player 1");
        }
    }

    /**
     * Spielablauf entsprechend Anfangszug, Folgezug, Bonuszug
     *
     * @param spieler ist 1 (Spielsteine 1 bis 12) oder -1 (Spielsteine -1 bis
     *                -12)
     */
    private static void spielerZieht(int spieler) {
        //prompt the player to choose a piece
        String msg = "Please choose which piece you want to move. You are player " + spieler + ", so your pieces are numbered from ";
        if (spieler == 1) msg += "1 to 12";
        else msg += "-1 to -12";

        int currPiece = readInt(msg);
        //check if the choice was legal
        boolean legal = gueltigeEingabe(currPiece, spieler);
        while (!legal) {
            write(currPiece + " is not a legal input!");
            currPiece = readInt(msg);
            legal = gueltigeEingabe(currPiece, spieler);
        }

        //now the input is legal, continue: player moves the selected piece one row ahead
        int nextTurn = performMove(currPiece, 1, spieler, msg);

        //print the current game status
        System.out.println(output());

        //finally the player chose a move that could be executed. Now he possibly gets another one
        if (nextTurn > 0) {
            //prompt the player to choose a piece
            msg += "\n You have another move. Distance: " + nextTurn;
            currPiece = readInt(msg);
            //check if the choice was legal
            legal = gueltigeEingabe(currPiece, spieler);
            while (!legal) {
                write(currPiece + " is not a legal input!");
                currPiece = readInt(msg);
                legal = gueltigeEingabe(currPiece, spieler);
            }

            //this time a possible next move is dismissed
            performMove(currPiece, nextTurn, spieler, msg);

            //print the current game status
            System.out.println(output());
        }
    }

    /**
     * checks whether the player is allowed to do this move and handles full rows
     * @param currPiece the piece to be moved
     * @param distance the move's distance
     * @param spieler the player performing the move
     * @param msg the standard message that is displayed when the player is asked to choose a piece he wants to move
     * @return the extra turn's distance
     */
    private static int performMove(int currPiece, int distance, int spieler, String msg) {
        boolean legal = false;
        int nextTurn = setzeZug(currPiece, distance, true);
        while (nextTurn == -1) {
            //the player can't do this. A different move has to be done
            write("You can't move this piece. The destination row is full! Choose a different piece.");
            currPiece = readInt(msg);
            //check if the choice was legal
            legal = gueltigeEingabe(currPiece, spieler);
            while (!legal) {
                write(currPiece + " is not a legal input!");
                currPiece = readInt(msg);
                legal = gueltigeEingabe(currPiece, spieler);
            }

            //try again
            nextTurn = setzeZug(currPiece, nextTurn, true);
        }

        return nextTurn;
    }

    public static void main(String args[]) {

        initSpiel();
        System.out.println(output());

        //randomly pick the player that starts
        boolean player1 = (((int) Math.round(Math.random())) == 1);

        while (!spielende()) {
            spielerZieht(player1 ? 1 : -1);

            //switch player
            player1 = !player1;
        }

        //game has ended, calculate points
        zaehlePunkte();

        //output the game status one last time
        System.out.println(output());
    }
}
