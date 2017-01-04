package com.samuel.pgdp.game;

/**
 * Die Klasse Move repraesentiert einen einzelnen Zug.
 *
 * Es gibt zwei Konstruktoren. Einer bekommt
 * Ausgangsfeld und Zielfeld uebergeben, der andere
 * bekommt nur den eingegebenen Zug in der Form
 * <Ausgangsfeld><Zielfeld> als String uebergeben,
 * also z. B. "a7c5" fuer den Zug von "a7" nach "c5".
 */
public class Move {

    String from, to;
    private int moveSequence;

    public Move(String from, String to){
        this.from = from;
        this.to = to;
    }

    public Move(String move){
        //split into from and to and pass this to the other constructor
        this("" + move.charAt(0) + move.charAt(1), "" + move.charAt(2) + move.charAt(3));
    }

    @Override
    public String toString(){
        // Rueckgabe exakt in der Form <Ausgangsfeld><Zielfeld> als String,
        // also z. B. "b2b3" fuer den Zug eines Tiers von "b2" nach "b3".
        return from + to;
    }

    public void setMoveSequence(int moveSequence) {
        this.moveSequence = moveSequence;
    }

    public int getMoveSequence() {
        return moveSequence;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public boolean equals(Object other) {
        //other has to be a Move object with same from and to coordinates as this one
        return other instanceof Move && (from.equals(((Move) other).getFrom()) && to.equals(((Move) other).getTo()));
    }

}
