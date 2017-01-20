package com.samuel.pgdp.blatt11.password;

/**
 * Created by Samuel on 19.01.2017.
 */
public class NotEnoughNumber extends NotEnoughExc {

    public NotEnoughNumber(int should, int is) {
        super(should, is);
    }

    @Override
    public String toString() {
        return "Error: the password needs to have at least " + should + " numbers, but currently has " + is;
    }
}
