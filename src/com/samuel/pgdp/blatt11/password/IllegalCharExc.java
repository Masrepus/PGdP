package com.samuel.pgdp.blatt11.password;

/**
 * Created by Samuel on 19.01.2017.
 */
public class IllegalCharExc extends Exception {

    private final char used;

    public IllegalCharExc(char used) {
        this.used = used;
    }

    @Override
    public String toString() {
        String notDisplayable = "";

        switch (used) {
            case '\n':
                notDisplayable = "newline (\\n)";
                break;
            case '\t':
                notDisplayable = "tab (\\t)";
                break;
            case '\r':
                notDisplayable = "carriage return (\\r)";
                break;
            case '\b':
                notDisplayable = "backspace (\\b)";
                break;
            case '\f':
                notDisplayable = "formfeed (\\f)";
                break;
        }

        return "Error: your password uses the illegal character " + (notDisplayable.equals("") ? used : notDisplayable);
    }
}
