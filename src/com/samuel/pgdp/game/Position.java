package com.samuel.pgdp.game;

/**
 * Die Klasse Position repraesentiert eine Spielsituation.
 */
public class Position {

    /**
     * Die Tiere werden intern in einem Array gespeichert.
     * nrAnimals gibt an, wie viele Tiere auf dem Brett sind.
     * Diese sind in myAnimals an den Positionen 0 bis nrAnimals-1 enthalten.
     * <p>
     * Es ist empfohlen, aber nicht vorgeschrieben, diese Attribute zu verwenden.
     * <p>
     * Falls die beiden Attribute NICHT verwendet werden, muss die Ausgabe
     * der Spielposition unten entsprechend auf die verwendete Datenstruktur
     * angepasst werden. Die toString-Methode darf dabei nicht veraendert werden,
     * muss jedoch die selbe Rueckgabe liefern. D.h. es ist dann notwendig,
     * die Hilfsmethode boardRepresentation auf die verwendete Datenstruktur anzupassen.
     */
    private Animal[] myAnimals;
    private int nrAnimals;
    public static final String backRow = "sehllhes";
    public static final String frontRow = "pkkkkkkp";

    /**
     * Spieler, der als naechstes ziehen darf ('M' oder 'W').
     * Wird jedes Mal aktualisiert, wenn eine Seite ihre Zuege ausfuehrt.
     */
    private char next = 'W';

    private char startingPlayer;

    private Game game;


    /**
     * Stellt die Anfangsposition des Spiels her.
     * Der Parameter gibt an, welche Seite beginnt ('M' oder 'W').
     */
    public void reset(char movesNext) {
        next = movesNext;
        startingPlayer = next;

        //we have 16 animals for each team
        nrAnimals = 32;

        //init the animal positions
        myAnimals = new Animal[32];
        //females front row
        int nextAnimal = initAnimals(true, true, 0);
        //females back row
        nextAnimal = initAnimals(true, false, nextAnimal);
        //males front row
        nextAnimal = initAnimals(false, true, nextAnimal);
        //males back row
        initAnimals(false, false, nextAnimal);
    }

    /**
     * Sets one row of animals to the default value for a given gender
     *
     * @param females     whether a row of females or a row of males should be initialized
     * @param useFrontRow whether the front row or the back row of animals is to be initialized
     * @param currAnimal  the first place in myAnimals that is currently empty
     * @return the next empty position in myAnimals after this initialization
     */
    private int initAnimals(boolean females, boolean useFrontRow, int currAnimal) {
        //both parties have the same animals in their front and back row, but the row numbers are different
        int front = !females ? 7 : 2;
        int back = !females ? 8 : 1;

        //start with front row
        char column = 'a';
        int row = useFrontRow ? front : back;

        for (int i = 0; i < (useFrontRow ? frontRow : backRow).length(); i++) {

            Animal animal = new Animal(females);

            //set the animal's race
            switch ((useFrontRow ? frontRow : backRow).charAt(i)) {
                case 'k':
                    animal = new Rabbit(females);
                    break;
                case 'p':
                    animal = new Penguin(females);
                    break;
                case 's':
                    animal = new Snake(females);
                    break;
                case 'e':
                    animal = new Elephant(females);
                    break;
                case 'h':
                    animal = new Horse(females);
                    break;
                case 'l':
                    animal = new Leopard(females);
                    break;
            }

            //now set the curent position
            animal.setSquare("" + column + row);
            animal.setPosition(this);

            //done, now we can add it
            myAnimals[currAnimal] = animal;
            currAnimal++;
            column++;
        }

        //return the next animal id
        return currAnimal;
    }


    /**
     * Fuehrt die uebergebenen Zuege fuer einen der Spieler aus.
     * Die Reihenfolge soll keinen Unterschied machen.
     * Diese Methode geht davon aus, dass dies bereits ueberprueft wurde.
     * <p>
     * Der Zustand des Spiels wird entsprechend angepasst, d. h. ein Spiel
     * kann von der Anfangsposition aus allein mittels Aufrufen dieser Methode
     * gespielt werden. Insbesondere wechselt durch den Aufruf das Zugrecht,
     * da M und W abwechselnd ziehen.
     *
     * @param moves Array mit den Zuegen, die ausgefuehrt werden sollen.
     */
    public void applyMoves(Move[] moves) {
        int vegRemaining = 3, predRemaining = 1;
        List<Move> toExecute = new List<>();

        for (Move move : moves) {
            if (move == null) continue;

            //check the format
            if (!Game.formatIsLegal(move.toString())) {
                System.out.println("The format of " + move + " is not legal, not applying this move!");
                continue;
            }

            //the move's format is legal, now check if the player is allowed to make this move with the selected animal
            Animal movingAnimal = getAnimal(move.getFrom());

            //check if this animal exists
            if (movingAnimal == null) {
                System.out.println("There is no animal at " + move.getFrom() + ", not executing this move!");
                continue;
            }

            //check if this animal belongs to the current player
            if (!movingAnimal.getGender().equals(next == 'W' ? "female" : "male")) {
                System.out.println("Animal " + move.getFrom() + " does not belong to this player. Not executing the move!");
                continue;
            }

            //check if this animal is allowed to move (only 3 vegetarians and 1 predator per round!)
            if (movingAnimal instanceof Vegetarian && vegRemaining > 0 || movingAnimal instanceof Predator && predRemaining > 0) {
                //animal is allowed to move, now check if it is allowed to move to the desired destination
                if (movingAnimal.isMoveLegal(move.getTo())) {
                    //move is ok, add it to the execution list
                    toExecute.add(move);

                    //decrease the correct "remaining" counter
                    if (movingAnimal instanceof Vegetarian) vegRemaining--;
                    else predRemaining--;
                    System.out.println("Move " + move + " has been executed");
                } else {
                    //this move is not legal
                    System.out.println("You can't move animal " + movingAnimal.getSquare() + " to " + move.getTo() + ", Choose again!");
                }
            } else {
                //tell the player that he can not move any more vegetarians or predators if the corresponding counter is 0
                System.out.println("Your chosen animal is a " + (movingAnimal instanceof Vegetarian ? "vegetarian" : "predator") + ", but you are not allowed to move any more animals of this kind! Move " + move + " not executed!");
            }
        }

        //convert to arra
        Move[] toExecuteArray = new Move[toExecute.length()];
        for (int i = 0; i < toExecute.length(); i++) {
            toExecuteArray[i] = toExecute.get(i);
        }

        executeMoves(toExecuteArray);

        //print the game infos
        System.out.println(this);
        printDaysRemaining();

        //check if we have to invoke sunset
        if (next == startingPlayer) doSunset();
    }

    /**
     * Executes the moves it gets passed without further testing of their legality. This method also sets {@link #next} correctly after having applied the moves. But it does NOT invoke sunset or print any game information. So the game cannot be played only by invoking this method. If you would like to do this, use {@link #applyMoves(Move[])} instead.
     *
     * @param moves an array of moves, preferrably with no empty places, but this method can handle this, too
     */
    public void executeMoves(Move[] moves) {
        for (Move move : moves) {
            if (move == null) continue;
            //get the animal to be moved, is not null because the calling method has to take care of the move's legality!
            Animal movingAnimal = getAnimal(move.getFrom());
            //get the animal at the destination point, if there is any
            Animal targetAnimal = getAnimal(move.getTo());

            //move the animal to the destination square and kill any animal that was there before
            //if there is an animal at the destination, we can assume that movingAnimal is a predator. This has to be checked by method callers!
            if (targetAnimal != null) ((Predator) movingAnimal).eat(targetAnimal);

            //now the destination is empty, move the animal there
            movingAnimal.setSquare(move.getTo());
        }

        next = next == 'W' ? 'M' : 'W';
    }


    /**
     * Ermittelt, ob/wer gewonnen hat.
     *
     * @return 'W' falls W gewonnen hat,
     * 'M' falls M gewonnen hat,
     * 'N' falls das Spiel unentschieden zu Ende ist,
     * 'X' falls das Spiel noch nicht zu Ende ist.
     */
    public char theWinner() {
        //check if there are no animals left
        if (myAnimals.length == 0) return 'N';

        //check if one player has no animals but the other one does
        String gender = myAnimals[0].getGender();
        for (Animal animal : myAnimals) {
            if (!animal.getGender().equals(gender)) return checkPredators();
        }

        //only animals of one gender on the board, finish the game
        return gender.equals("female") ? 'W' : 'M';
    }

    private char checkPredators() {
        //check if there are still predators
        for (Animal animal : myAnimals) {
            if (animal instanceof Predator) return 'X';
        }

        //no predators left, now the one with more animals wins
        int females = 0, males = 0;
        for (Animal animal : myAnimals) {
            if (animal.getGender().equals("female")) females++;
            else males++;
        }

        return females == males ? 'N' : females > males ? 'W' : 'M';
    }


    // Ausgabe der Spielposition

    private static final int[] I = {8, 7, 6, 5, 4, 3, 2, 1};
    private static final String[] J = {"a", "b", "c", "d", "e", "f", "g", "h"};

    private static int toIndex(String s) {
        return (s.charAt(0) - 'a');
    }

    // Erzeugt eine 2D-Repraesentation der Spielposition.
    // Darf ggf. auf neue Datenstruktur angepasst werden (s.o.)
    // Die Rueckgabe ist ein zweidimensionales Array, welches
    // jedem Feld das darauf befindliche Tier (oder null) zuordnet.
    // Dabei laeuft der erste Index von der 'a'-Linie zur 'h'-Linie,
    // der zweite von der 1. zur 8. Reihe. D.h. wenn z.B. bei a[3][7]
    // ein Tier ist, ist das zugehörige Feld "d8" (vierter Buchstabe,
    // achte Zahl).
    public Animal[][] boardRepresentation() {
        Animal[][] a = new Animal[8][8];
        for (int i : I) {
            for (String j : J) {
                for (int k = 0; k < myAnimals.length; k++) {
                    if (null == myAnimals[k]) {
                        break;
                    }
                    if (myAnimals[k].square.equals(j + i)) {
                        a[toIndex(j)][i - 1] = myAnimals[k];
                    }
                }
            }
        }
        return a;
    }


    @Override
    public String toString() {
        String str = "   a b c d e f g h\n";
        Animal[][] ani = boardRepresentation();
        for (int i : I) {
            str += (i + " ");
            for (String j : J) {
                if (null == ani[toIndex(j)][i - 1]) {
                    str += (i + toIndex(j)) % 2 == 1 ? Globals.ts_empty_square_dark : Globals.ts_empty_square_light;
                } else {
                    str += ani[toIndex(j)][i - 1].toString();
                }
            }
            str += " " + i + "\n";
        }
        str += "   a b c d e f g h\nIt is " + next + "'s turn.\n";
        return str;
    }

    /**
     * Removes a given animal from the board
     *
     * @param square the position of the animal to be killed
     */
    public void kill(String square) {
        //copy the animals array and remove the animal to be killed
        Animal[] tmp = new Animal[nrAnimals - 1];

        int i = 0;

        for (Animal a : myAnimals) {
            if (!a.getSquare().equals(square)) {
                tmp[i] = a;
                i++;
            }
        }

        myAnimals = tmp;
        nrAnimals--;
    }

    /**
     * Invokes {@link Animal#sunset()} for all animals in {@link #myAnimals}
     */
    public void doSunset() {
        for (Animal a : myAnimals) {
            a.sunset();
        }
    }

    /**
     * Prints a message displaying the number of remaining days without food for each predator
     */
    public void printDaysRemaining() {
        System.out.println("Days remaining without food:");
        for (int i = 0; i < myAnimals.length; i++) {
            Animal a = myAnimals[i];

            if (a instanceof Predator) {
                //get the animal's infos and print them, this is a predator
                String species = "";
                if (a instanceof Leopard) species = "Leopard";
                else if (a instanceof Penguin) species = "Penguin";
                else if (a instanceof Snake) species = "Snake";

                System.out.print(a.getSquare() + " (" + a.getGender() + " " + species + "): " + ((Predator) a).getDaysRemaining());
                if (i < myAnimals.length - 1) System.out.print(", ");
            }
        }
    }

    /**
     * Find a specific animal
     * @param square the animal's position on the board
     * @return the desired animal
     * null if there is no animal at the specified square
     */
    public Animal getAnimal(String square) {
        //iterate through all animals and search for the right square
        for (Animal a : myAnimals) {
            if (a.getSquare().equals(square)) return a;
        }

        //nothing found, return null
        return null;
    }

    public char getNext() {
        return next;
    }

    /**
     * Prints all possible moves for all animals that belong to the current player. So {@link Animal#printPossibleMoves(boolean)} is being invoked for all animals in {@link #myAnimals}
     */
    public void printPossibleMoves() {
        System.out.println("Here are all possible moves for your animals. The animal's current position is marked with an X, possible destinations have a checkmark (✓)");
        String gender = next == 'W' ? "female" : "male";
        //print the possible moves for all animals of this gender
        for (Animal animal : myAnimals) {
            if (animal.getGender().equals(gender)) {
                System.out.println("\n" + animal.square + ":");

                //first print the written form, then the graphical representation
                Move[] moves = animal.possibleMoves();
                for (Move move : moves) System.out.print(move + " ");
                System.out.println();

                animal.printPossibleMoves(false);
            }
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
