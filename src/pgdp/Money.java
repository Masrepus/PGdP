package pgdp;

/**
 * Created by samuel on 16.12.16.
 */
public class Money {

    private int cent;

    public Money() {

    }

    public Money(int cent) {
        this.cent = cent;
    }

    public int getCent() {
        return cent;
    }

    public Money addMoney(Money m) {
        return new Money(cent + m.getCent());
    }

    @Override
    public String toString() {
        //get digits after the comma
        String cents = "" + cent;
        while (cents.length() < 2) {
            //fill up with leading zeros
            cents = "0" + cents;
        }

        if (cents.length() == 2) return "0," + cents + " Euro";
        else if (cents.length() == 3 && cent < 0) return "-0," + -cent + " Euro";

        //we have digits before the comma, so format the String accordingly
        String euros = "";
        for (int i = 0; i < cents.length() - 2; i++) {
            euros += cents.charAt(i);
        }

        return euros + "," + cents.charAt(cents.length() - 2) + cents.charAt(cents.length() - 1) + " Euro";
    }

    public static void main(String[] args) {
    }
}
