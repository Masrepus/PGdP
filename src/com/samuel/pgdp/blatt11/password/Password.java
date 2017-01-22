package com.samuel.pgdp.blatt11.password;

/**
 * Created by samuel on 20.01.17.
 */
public class Password {

    private final int nrUpperShould, nrLowerShould, nrSpecialShould, nrNumbersShould, lengthShould;
    private final char[] illegalChars;

    public static void main(String[] args) {
        Password p = new Password(2, 2, 2, 2, 8, new char[]{'\n', '\t', '\b', '\f', '\r'});
        try {
            p.checkFormat("abcABC123ÄÖü!?");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Password(int nrUpperShould, int nrLowerShould, int nrSpecialShould, int nrNumbersShould, int lengthShould, char[] illegalChars) {
        this.nrUpperShould = nrUpperShould;
        this.nrLowerShould = nrLowerShould;
        this.nrSpecialShould = nrSpecialShould;
        this.nrNumbersShould = nrNumbersShould;
        this.lengthShould = lengthShould;
        this.illegalChars = illegalChars;
    }

    public void checkFormat(String pwd) throws IllegalCharExc, NotEnoughExc, NotLongEnoughExc {

        if (pwd == null) throw new NullPointerException();

        int numbers = 0, lowercase = 0, uppercase = 0, special = 0;

        //test length
        if (pwd.length() < lengthShould) throw new NotLongEnoughExc(lengthShould, pwd.length());

        for (int i = 0; i < pwd.length(); i++) {
            char c = pwd.charAt(i);

            //test if illegal
            for (int j = 0; j < illegalChars.length; j++) {
                if (c == illegalChars[j]) throw new IllegalCharExc(c);
            }

            //legal char, check if letter (upper or lower) or number
            if (c <= '9' && c >= '0') numbers++;
            else if ((c <= 'Z' && c >= 'A') || c == 'Ä' || c == 'Ö' || c == 'Ü') uppercase++;
            else if ((c <= 'z' && c >= 'a') || c == 'ä' || c == 'ö' || c == 'ü') lowercase++;
            else special++;
        }

        //now check if there are enough characters of each kind
        if (uppercase < nrUpperShould) throw new NotEnoughUpper(nrUpperShould, uppercase);
        if (lowercase < nrLowerShould) throw new NotEnoughLower(nrLowerShould, lowercase);
        if (numbers < nrNumbersShould) throw new NotEnoughNumber(nrNumbersShould, numbers);
        if (special < nrSpecialShould) throw new NotEnoughSpecial(nrSpecialShould, special);
    }
}
