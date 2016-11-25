package com.samuel.pgdp.blatt5;

import com.samuel.pgdp.MiniJava;

public class Linja extends MiniJava {

    /**
     * numberPieces contains the number of pieces still in the game, not Displayed the ones that are not there anymore.
     * Those two arrays HAVE to be updated if a piece exits the visible game area!
     */
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
     * Checks whether the piece that was chosen has a value that is legal for this player
     * @param stein the piece to check the legality for
     * @param spieler player id, either 1 or -1
     * @return true, if the choice was legal, false if not
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
     * @param stein the piece to move
     * @param weite the distance to move {@code stein}
     * @param vorwaerts == true: Zug erfolgt vorwaerts aus Sicht des
     *                  Spielers/Steins vorwearts == false: Zug erfolgt rueckwaerts aus Sicht des
     *                  Spielers/Steins
     * @return Rueckgabe -1: Zug nicht zulaessig Rueckgabe 0-5: Weite des
     * potentiellen naechsten Zugs (falls Folgezug folgt) Rueckgabe 6: Ziellinie
     * wurde genau getroffen (potentieller Bonuszug)
     */
    private static int setzeZug(int stein, int weite, boolean vorwaerts) {
        //check where the piece is right now
        int[] currPosition = findeStein(stein);
        if (currPosition[0] == -1 && currPosition[1] == -1) return -1;

        //which row is the "finish line" and the "starting point" for this piece?
        int finishLine = (stein < 0) ? 0 : 7;
        int start = (stein < 0) ? 7 : 0;

        //calculate the destination row
        int destRow;
        if (vorwaerts) {
            if (stein < 0) destRow = currPosition[0] - weite;
            else destRow = currPosition[0] + weite;
        } else {
            if (stein < 0) destRow = currPosition[0] + weite;
            else destRow = currPosition[0] - weite;
        }

        //you can't move a piece if it has already crossed the finish line
        if (currPosition[0] == finishLine) return -1;
        //you can't move a piece backward if it is still in the starting row
        else if (!vorwaerts && currPosition[0] == start) return -1;

        //check if destRow is still between 0 and 7 or exactly 7 (potential bonus round)
        if (stein > 0 && destRow > 7 || stein < 0 && destRow < 0) {
            //remove the piece from the game
            spielfeld[currPosition[0]][currPosition[1]] = 0;

            //increase the number of pieces that are not displayed
            if (stein < 0) notDisplayed[0]++;
            else notDisplayed[1]++;

            //decrease the number of pieces stil in the game
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
                if (number1 == numberPieces[1] || numberMinus1 == numberPieces[0]) return true; //we found all pieces of one color without finding one of the other color ==> end of game
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
            String winner = (points1 > pointsMinus1) ? "Player 1 has won! " : "Player -1 has won! ";
            //output the points
            write("The game has ended. " + winner + "\n" + pointsMinus1 + " (player -1) : " + points1 + " (player 1)");
        }
    }

    /**
     * Spielablauf entsprechend Anfangszug, Folgezug, Bonuszug
     *
     * @param spieler ist 1 (Spielsteine 1 bis 12) oder -1 (Spielsteine -1 bis
     *                -12)
     * @param bonus whether we use the bonus rule
     */
    private static void spielerZieht(int spieler, boolean bonus) {
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

        //check if this is a bonus round
        if (bonus && nextTurn == 6) handleBonus(spieler);
        else if (nextTurn > 0) {
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

            //this time a possible next move is dismissed, unless we get a bonus move (if we use the bonus rule)
            nextTurn = performMove(currPiece, nextTurn, spieler, msg);

            //print the current game status
            System.out.println(output());

            if (bonus && nextTurn == 6) handleBonus(spieler);
        }
    }

    /**
     * Performs the bonus round. Player can choose whether to move any piece forward or backward by one row (except pieces that have passed the finish line)
     * @param player the player performing the bonus round
     */
    private static void handleBonus(int player) {

        int code;

        do {
            //prompt the player to choose a piece they would like to use for their bonus round
            String msg = "You get a bonus round! You are now allowed to move one piece (one of own or one of your opponents) - either one row forward or one row backward. First choose which piece you would like to move\n" +
                    "You are player " + player + ", so your pieces are numbered from " + ((player == -1) ? "-1 to -12." : "1 to 12.");
            int currPiece = readInt(msg);

            //check if the choice was legal, this time pieces from every player can be used
            while (!(currPiece >= -12 && currPiece != 0 && currPiece <= 12)) {
                write(currPiece + " is not a legal input!");
                currPiece = readInt(msg);
            }

            //the choice was legal, so now ask if forward or backward
            msg = "Now choose whether forward (1) or backward (0). The piece's 'forward' is " + ((currPiece < 0) ? "from bottom to top" : "from top to bottom");
            int direction = readInt(msg);

            //check if legal
            while (direction != 0 && direction != 1) {
                write(direction + " is not a legal input!");
                direction = readInt(msg);
            }

            //now we also have the direction, so try to perform the move
            boolean vorwaerts = (direction == 1);

            //if this returns -1, we do it all again
            code = setzeZug(currPiece, 1, vorwaerts);
            if (code == -1) write("You can't move piece " + currPiece + (vorwaerts ? " forward!" : " backward!"));
        } while (code == -1);

        //bonus round ends the player's turn, so just print the current status
        System.out.println(output());
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
            write("You can't move this piece! Choose a different piece.");
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

        //ask if bonus rule should be used
        int bonus = readInt("Would you like to use the bonus rule? 1 = yes, 0 = no");
        //check if legal
        while (bonus != 0 && bonus != 1) {
            write(bonus + " is not a legal input!");
            bonus = readInt("Would you like to use the bonus rule? 1 = yes, 0 = no");
        }

        //randomly pick the player that starts
        boolean player1 = (((int) Math.round(Math.random())) == 1);

        while (!spielende()) {
            spielerZieht(player1 ? 1 : -1, bonus == 1);

            //switch player
            player1 = !player1;
        }

        //game has ended, calculate points
        zaehlePunkte();

        //output the game status one last time
        System.out.println(output());
    }
}
