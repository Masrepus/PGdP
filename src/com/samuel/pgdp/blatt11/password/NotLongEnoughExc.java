package com.samuel.pgdp.blatt11.password;

/**
 * Created by Samuel on 19.01.2017.
 */
public class NotLongEnoughExc extends Exception {

    private final int should, is;

    public NotLongEnoughExc(int should, int is) {
        this.should = should;
        this.is = is;
    }

    @Override
    public String toString() {
        return "Error: the password should be of length " + should + ", but is of length " + is;
    }
}
