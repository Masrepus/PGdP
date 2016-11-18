package com.samuel.pgdp.blatt4;

import com.samuel.pgdp.MiniJava;

public class Verhext extends MiniJava {

    public Verhext() {
        String input = readString("Enter the hexadecimal number you would like to convert to Integer");

        //first check for underscores and eliminate them from the String
        String cleanInput = preprocess(input);
        //calculate the integer output and print it
        int output = convert(cleanInput, cleanInput.length()-1);

        //check if there is a '-' at the beginning of the String
        if (cleanInput.charAt(0) =='-') output *= -1;

        write(input + " in Integer is " + output);
    }

    private String preprocess(String input) {
        String cleanInput = "";

        //iterate through the String and delete underscores
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '_') cleanInput += input.charAt(i);
        }

        return cleanInput;
    }

    // x^y
    private int pow(int x, int y) {
        return java.math.BigInteger.valueOf(x).pow(y).intValueExact();
    }

    public static void main(String[] args) {
        new Verhext();
    }

    private int convert(String input, int i) {
        //check if we reached the 0x at the beginning of the string and abort if so
        if (input.charAt(i) == 'x') return 0;

        //take the char at position i and replace it with the corresponding hex value
        int digit = getHexValue(input.charAt(i));

        //now calculate the required value based on the digit's position
        digit *= pow(16, (input.length()-1) - i);

        //now recursively go on calculating
        return digit + convert(input, i - 1);
    }

    private int getHexValue(char c) {
        //if it's a digit from 0-9, just pass it through
        String digits = "0123456789";
        for (int i = 0; i <= 9; i++) {
            if (c == digits.charAt(i)) return i;
        }

        //c is a letter, so calculate the decimal value
        switch (c) {
            case 'a':
            case 'A':
                return 10;
            case 'b':
            case 'B':
                return 11;
            case 'c':
            case 'C':
                return 12;
            case 'd':
            case 'D':
                return 13;
            case 'e':
            case 'E':
                return 14;
            case 'f':
            case 'F':
                return 15;
        }

        //we won't get here
        return 0;
    }
}