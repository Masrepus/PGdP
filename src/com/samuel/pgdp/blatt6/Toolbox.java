package com.samuel.pgdp.blatt6;

import com.samuel.pgdp.MiniJava;

public class Toolbox extends MiniJava {

    public static void main(String[] args) {
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
            else
                return performMultiplication(y, x); //x >= 0 so we just swap x and y and we get a non-negative number for performMultiplication's y
        } else return performMultiplication(x, y);
    }

    /**
     * recursively multiplies two integers
     *
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
     *
     * @param m the array to be reversed
     * @param i the current position in the array, is being incremented
     */
    public static void reverse(int[] m, int i) {
        //swap array entries symmetrically until we reach the center
        if (i <= m.length / 2) {
            int backup = m[i];
            m[i] = m[m.length - 1 - i];
            m[m.length - 1 - i] = backup;
            reverse(m, i + 1);
        }
    }

    public static int numberOfOddIntegers(int[] m) {
        return numberOfOddIntegers(m, 0);
    }

    /**
     * counts the number of odd integers in an array
     *
     * @param m the array where we should count
     * @param i the current position in {@code m}
     * @return the number of odd integers in {@code m}
     */
    public static int numberOfOddIntegers(int[] m, int i) {
        if (i > m.length - 1) return 0;
        else {
            if (isOdd(m[i])) return 1 + numberOfOddIntegers(m, i + 1);
            else return numberOfOddIntegers(m, i + 1);
        }
    }

    /**
     * flip-flops between isEven and isOdd to determine if an int is even
     *
     * @param i an int
     * @return true if {@code i} is even
     */
    public static boolean isEven(int i) {
        if (i == 0) return true;
        else return isOdd((i < 0) ? i + 1 : i - 1); //we want to get closer to 0, so count up if <0 and down if >0
    }

    /**
     * filp-flops between isOdd and isEven to determine if an int is odd
     *
     * @param i an int
     * @return true if {@code i} is odd
     */
    public static boolean isOdd(int i) {
        if (i == 0) return false;
        else return isEven((i < 0) ? i + 1 : i - 1); //we want to get closer to 0, so count up if <0 and down if >0
    }

    public static int[] filterOdd(int[] m) {
        return filterOdd(m, new int[0], 0);
    }

    /**
     * finds all odd numbers in an integer array and puts them in a new array
     *
     * @param m    the array that should be scanned for odd numbers
     * @param newM a temporary array that serves as "working space"
     * @param i    the current position in {@code m}
     * @return a new array containing all odd numbers from {@code m} sorted by position in {@code m}
     */
    private static int[] filterOdd(int[] m, int[] newM, int i) {
        if (i > m.length - 1) return newM;
        else {
            if (isOdd(m[i])) {
                //create a new temp array that is one bigger than newM
                int[] temp = new int[newM.length + 1];
                //copy all items of newM into temp
                addAll(temp, newM, 0);
                //add this new odd number to the array and continue with the next position in m
                temp[newM.length] = m[i];
                return filterOdd(m, temp, i + 1);
            } else return filterOdd(m, newM, i + 1);
        }
    }

    /**
     * Recursively copies the content of one array into another one of at least the same size
     *
     * @param dest   an integer array that should receive the copied data
     * @param source the source array. All data it contains is being copied to {@code dest}
     * @param i      the current index position. Start with 0
     */
    private static void addAll(int[] dest, int[] source, int i) {
        if (i <= dest.length - 1 && i <= source.length - 1) {
            dest[i] = source[i];
            addAll(dest, source, i + 1);
        }
    }
}
