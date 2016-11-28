package com.samuel.pgdp.blatt6;

import com.samuel.pgdp.MJAutoscreen;

public class Toolbox extends MJAutoscreen {

    public static void main(String[] args) {
        int[] test = new int[]{8,8,8,1,8,8,8,1,8,8,8,1};
        System.out.println(numberOfOddIntegers(test));
    }

    public static int evenSum(int n) {
        return (n < 0) ? evenSumNegative(n, 0) : evenSumPositive(n, 0);
    }

    public static int evenSumNegative(int n, int current) {
        if (current < n) return 0;
        else return current + evenSumNegative(n, current - 2);
    }

    public static int evenSumPositive(int n, int current) {
        if (current > n) return 0;
        else return current + evenSumPositive(n, current + 2);
    }

    public static int multiplication(int x, int y) {
        //performMultiplication expects y to be >= 0 because it is being decremented, so we have to make sure that we find a way to make it positive
        if (y < 0) {
            if (x < 0) return performMultiplication(-x, -y); //-*- == +
            else return performMultiplication(y, x); //x >= 0 so we just swap x and y and we get a non-negative number for performMultiplication's y
        } else return performMultiplication(x, y);
    }

    /**
     * recursively multiplies two integers
     * @param x any int
     * @param y any NON-NEGATIVE int
     * @return x*y
     */
    public static int performMultiplication(int x, int y) {
        if (y == 0) return 0;
        else return x + multiplication(x, y - 1);
    }

    public static void reverse(int[] m) {
        reverse(m, 0);
    }

    /**
     * recursively reverses an array
     * @param m the array to be reversed
     * @param i the current position in the array, is being incremented
     */
    public static void reverse(int[] m, int i) {
        //swap array entries symmetrically until we reach the center
        if (i <= m.length/2) {
            int backup = m[i];
            m[i] = m[m.length-1-i];
            m[m.length-1-i] = backup;
            reverse(m, i + 1);
        }
    }

    public static int numberOfOddIntegers(int[] m) {
        return numberOfOddIntegers(m, 0);
    }

    /**
     * counts the number of odd integers in an array
     * @param m the array where we should count
     * @param i the current position in {@code m}
     * @return the number of odd integers in {@code m}
     */
    public static int numberOfOddIntegers(int[] m, int i) {
        if (i > m.length-1) return 0;
        else {
            if (isOdd(m[i])) return 1 + numberOfOddIntegers(m, i+1);
            else return numberOfOddIntegers(m, i+1);
        }
    }

    /**
     * flip-flops between isEven and isOdd to determine if an int is even
     * @param i an int
     * @return true if {@code i} is even
     */
    public static boolean isEven(int i) {
        if (i == 0) return true;
        else return isOdd((i < 0) ? i+1 : i-1); //we want to get closer to 0, so count up if <0 and down if >0
    }

    /**
     * filp-flops between isOdd and isEven to determine if an int is odd
     * @param i an int
     * @return true if {@code i} is odd
     */
    public static boolean isOdd(int i) {
        if (i == 0) return false;
        else return isEven((i < 0) ? i+1 : i-1); //we want to get closer to 0, so count up if <0 and down if >0
    }

    public static int[] filterOdd(int[] m) {
        return null;
    }
}
