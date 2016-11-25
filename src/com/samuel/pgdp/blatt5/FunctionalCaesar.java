package com.samuel.pgdp.blatt5;

import com.samuel.pgdp.MiniJava;

public class FunctionalCaesar extends MiniJava {

    private static String lower = "abcdefghijklmnopqrstuvwxyz";
    private static String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * cyclically shifts a {@code c} by the given {@code shift} if it is included in our alphabet. If not, {@code c} is returned as is
     * @param c the char to be processed
     * @param k the cyclical shift as an Integer
     * @return the processed char
     */
    public static char shift(char c, int k) {
        //find the index of this char and whether it is in lower or upper case
        for (int i = 0; i < lower.length(); i++) {
            if (lower.charAt(i) == c) {
                //caesar encryption formula as said by Wikipedia
                int newId;
                if (k >=0) newId = (i + k) % 26;
                else newId = (i + 26 + k) % 26;
                return (lower.charAt(newId));
            }
        }

        //seems like the char is not lowercase, try uppercase now
        for (int i = 0; i < upper.length(); i++) {
            if (upper.charAt(i) == c) {
                //caesar encryption formula as said by Wikipedia
                int newId;
                if (k >=0) newId = (i + k) % 26;
                else newId = (i + 26 + k) % 26;
                return (upper.charAt(newId));
            }
        }

        //if we got until here, the char is no element of the alphabet. Just return the original char
        return c;
    }

    /**
     * Encrypts a String using the Caesar algorithm
     * @param s a String to encrypt
     * @param k the cyclical shift to be used
     * @return the encrypted version of {@code s}
     */
    public static String encrypt(String s, int k) {

        char[] chars = new char[s.length()];

        //iterate through the chars of message
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //"encrypt" this char
            chars[i] = shift(c, k);
        }

        //now assemble the chars to a new string
        String newMessage = "";
        for (char c : chars) {
            newMessage += c;
        }

        return newMessage;
    }

    /**
     * Decrypts a String using the Caesar algorithm
     * @param s the String to decrypt
     * @param k the cyclical shift it was encrypted with
     * @return the decrypted String
     */
    public static String decrypt(String s, int k) {
        return encrypt(s, -(k % 26));
    }

    public static void main(String[] args) {
        String input = readString();
        int k = read();
        String out = encrypt(input, k);
        write(out);

        write("Mrn pnvnrwbcnw Jdopjknw bcnuuc Ajyqjnuj in clear text is:\n" +
                decrypt("Mrn pnvnrwbcnw Jdopjknw bcnuuc Ajyqjnuj", 9) + " (shift: 9)");
    }

}

