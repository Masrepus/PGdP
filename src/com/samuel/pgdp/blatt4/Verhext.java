package com.samuel.pgdp.blatt4;

import com.samuel.pgdp.MiniJava;

public class Verhext extends MiniJava {

    public Verhext() {
        String input = readString("Enter the hexadecimal number you would like to convert to Integer");

        //check if bonus version is needed
        int version = readInt("Enter 0 for standard version of 4.7 or 1 if you would like the program to comply with bonus requirements");
        while (version != 0 && version != 1) {
            write(version + " is invalid!");
            version = readInt("Enter 0 for standard version of 4.7 or 1 if you would like the program to comply with bonus requirements");
        }

        if (version == 1) verhextBonus(input);
        else verhextStandard(input);
    }

    private void verhextStandard(String input) {
        //first check for underscores and eliminate them from the String
        String cleanInput = removeUnderscores(input);
        //calculate the integer output and print it
        int output = convert(cleanInput, cleanInput.length() - 1);

        //check if there is a '-' at the beginning of the String
        if (cleanInput.charAt(0) == '-') output *= -1;

        write(input + " in Integer is " + output);
    }

    private void verhextBonus(String input) {
        //for bonus version, we first have to check whether the String is legal
        try {
            checkInput(input);

            //all seems fine, now continue parsing
            verhextStandard(input);
        } catch (Exception e) {
            write(e.getMessage());
        }
    }

    private void checkInput(String input) throws Exception {
        //if we only have 0x or -0x we can abort
        if (stringContentEquals(input, "0x") || stringContentEquals(input, "0X") || stringContentEquals(input, "-0x") || stringContentEquals(input, "-0X"))
            throw new Exception("Error: Hexadecimal numbers are required to have at least one hexadecimal digit!");

        String legalChars = "+-_xX0123456789aAbBcCdDeEfF";
        boolean xUsed = false;
        boolean signUsed = false;

        //iterate through the String
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '-' || c == '+') {
                //check if already used or at wrong position
                if (signUsed) throw new Exception("Error: Multiple sign chars!");
                if (i != 0) throw new Exception("Error: sign char at unexpected position!");
                else signUsed = true;
            }

            //now check if c is a legal char
            boolean charLegal = false;
            for (int j = 0; j < legalChars.length(); j++) {
                if (legalChars.charAt(j) == c) {
                    charLegal = true;
                    //if c is a x, remember this for later
                    if (c == 'x' || c == 'X') {
                        if (xUsed) throw new Exception("Error: Multiple occurences of 'x'!");
                        else xUsed = true;
                    }
                    break;
                }
            }

            if (!charLegal) throw new Exception("Error: Illegal char: '" + c + "' !");
        }

        //now check that we have 0x or -0x at the beginnig of our String
        if (!(input.charAt(0) == '0' && input.charAt(1) == 'x' || input.charAt(0) == '-' && input.charAt(1) == '0' && input.charAt(2) == 'x'))
            throw new Exception("Error: Expected \"0x\" or \"-0x\" at start of a hexadecimal number!");

        //does the number fit into Integer? First we have to clean it completely, so remove all underscores or leading zeros
        String cleanInput = removeUnderscores(input);
        cleanInput = removeLeadingZeros(cleanInput);

        //if the String's length is longer than 0x7fffffff it won't fit into int anyways
        if (cleanInput.charAt(0) == '-') {
            //we have a minus, so take that into account
            if (cleanInput.length() > "-0x7fffffff".length()) throw new Exception("Error: " + input + " is too small for Integer!");
            else if (cleanInput.length() == "-0x7fffffff".length()){
                //so the String's length is at the limit, check if it fits numerically, nearly same thing as below
                switch (cleanInput.charAt(3)) {
                    case '8':
                    case '9':
                    case 'A':
                    case 'a':
                    case 'B':
                    case 'b':
                    case 'C':
                    case 'c':
                    case 'D':
                    case 'd':
                    case 'E':
                    case 'e':
                    case 'F':
                    case 'f':
                        throw new Exception("Error: " + input + " is too small for Integer!");
                }
            }
        } else {
            if (cleanInput.length() > "0x7fffffff".length())
                throw new Exception("Error: " + input + " is too big for Integer!");
            else if (cleanInput.length() == "0x7fffffff".length()){
                //so the String's length is at the limit, check if it fits numerically, this is not the case if the digit after 0x is bigger than or equal to 8
                switch (cleanInput.charAt(2)) {
                    case '8':
                    case '9':
                    case 'A':
                    case 'a':
                    case 'B':
                    case 'b':
                    case 'C':
                    case 'c':
                    case 'D':
                    case 'd':
                    case 'E':
                    case 'e':
                    case 'F':
                    case 'f':
                        throw new Exception("Error: " + input + " is too big for Integer!");
                }
            }
        }
    }

    private String removeUnderscores(String input) {
        String cleanInput = "";

        //iterate through the String and delete underscores
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '_') cleanInput += input.charAt(i);
        }

        return cleanInput;
    }

    /**
     * removes leading zeros from a hexadecimal number
     * @param cleanInput HAS to be a hexadecimal number without underscores
     * @return {@code cleanInput} without leading zeros
     */
    private String removeLeadingZeros(String cleanInput) {
        String noLeadingZeros = "";
        //check whether the number has a minus char in front of it
        boolean hasMinus = cleanInput.charAt(0) == '-';

        //iterate through the String, but only start after 0x or -0x respectively
        boolean hadDigitDifferentFrom0 = false;
        int i;
        if (hasMinus) {
            i = 3;
            noLeadingZeros = "-0x";
        } else {
            i = 2;
            noLeadingZeros = "0x";
        }

        for (; i < cleanInput.length(); i++) {
            //skip all leading zeros, but when there has been one different digit add all following ones to our String
            if (cleanInput.charAt(i) == '0' && !hadDigitDifferentFrom0) {
                continue;
            } else {
                hadDigitDifferentFrom0 = true;
                noLeadingZeros += cleanInput.charAt(i);
            }
        }

        return noLeadingZeros;
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
        digit *= pow(16, (input.length() - 1) - i);

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

    /**
     * Compares two Strings as we are not allowed to use String.contentEquaLs()
     *
     * @param a a String
     * @param b a String
     * @return true if a.contentEquals(b)
     */
    private boolean stringContentEquals(String a, String b) {
        if (a.length() != b.length()) return false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) return false;
        }
        return true;
    }
}