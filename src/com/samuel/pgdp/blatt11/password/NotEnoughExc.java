package com.samuel.pgdp.blatt11.password;

/**
 * Created by Samuel on 19.01.2017.
 */
public class NotEnoughExc extends Exception {

    protected final int should, is;

    public NotEnoughExc(int should, int is) {
        this.should = should;
        this.is = is;
    }
}
