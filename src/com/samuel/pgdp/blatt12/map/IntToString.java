package com.samuel.pgdp.blatt12.map;

/**
 * Created by Samuel on 22.01.2017.
 */
public class IntToString implements Fun<Integer, String> {
    @Override
    public String apply(Integer x) {
        return "" + x;
    }
}
