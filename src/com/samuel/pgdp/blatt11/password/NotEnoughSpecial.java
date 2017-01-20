package com.samuel.pgdp.blatt11.password;

/**
 * Created by Samuel on 19.01.2017.
 */
public class NotEnoughSpecial extends NotEnoughExc {

    public NotEnoughSpecial(int should, int is) {
        super(should, is);
    }

    @Override
    public String toString() {
        return "Error: the password needs to have at least " + should + " special characters, but currently has " + is;
    }
}
