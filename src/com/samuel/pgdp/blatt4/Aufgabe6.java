package com.samuel.pgdp.blatt4;

import com.samuel.pgdp.MiniJava;

public class Aufgabe6 extends MiniJava {

    private static String lower = "abcdefghijklmnopqrstuvwxyz";
    private static String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        new Aufgabe6();
    }

    public Aufgabe6() {

        //read the string we should process, the cyclical shift, and ask whether it should be encrypted or decrypted
        String message = readString("Enter the message you would like to encrypt/decrypt");
        int mode = readInt("Please enter 1 to encrypt or 0 to decrypt");
        while (mode != 1 && mode != 0) {
            write(mode + " is invalid!");
            mode = readInt("Please enter 1 to encrypt or 0 to decrypt");
        }

        int shift = readInt("Enter the desired cyclical shift");

        if (mode == 1) encrypt(message, shift);
        //a text that has been encrypted with shift n can be decrypted by re-encrypting with shift -n
        else encrypt(message, -shift);
    }

    /**
     * Encrypts a string using the caesar-encryption with a cyclical shift
     * @param message the message to be encrypted
     * @param shift the cyclical shift that should be applied; use -shift to decrypt a message that has been encrypted with shift
     */
    private void encrypt(String message, int shift) {

        char[] chars = new char[message.length()];

        //iterate through the chars of message
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            //"encrypt" this char
            chars[i] = process(c, shift);
        }

        //now assemble the chars to a new string
        String newMessage = "";
        for (char c : chars) {
            newMessage += c;
        }

        //print it
        write("The processed message is:\n" + newMessage);
    }

    /**
     * cyclically shifts a {@code c} by the given {@code shift} if it is included in our alphabet. If not, {@code c} is returned as is
     * @param c the char to be processed
     * @param shift the cyclical shift as an Integer
     * @return the processed char
     */
    private char process(char c, int shift) {

        //find the index of this char and whether it is in lower or upper case
        for (int i = 0; i < lower.length(); i++) {
            if (lower.charAt(i) == c) {
                //caesar encryption formula as said by Wikipedia
                int newId;
                if (shift >=0) newId = (i + shift) % 26;
                else newId = (i + 26 + shift) % 26;
                return (lower.charAt(newId));
            }
        }

        //seems like the char is not lowercase, try uppercase now
        for (int i = 0; i < upper.length(); i++) {
            if (upper.charAt(i) == c) {
                //caesar encryption formula as said by Wikipedia
                int newId;
                if (shift >=0) newId = (i + shift) % 26;
                else newId = (i + 26 + shift) % 26;
                return (upper.charAt(newId));
            }
        }

        //if we got until here, the char is no element of the alphabet. Just return the original char
        return c;
    }
}
