package com.samuel.pgdp.blatt6;

import com.samuel.pgdp.MJAutoscreen;

public class Toolbox extends MJAutoscreen {

    public static void main(String[] args) {
        System.out.println(multiplication(5, -0));
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

    }

    public static int numberOfOddIntegers(int[] m) {
        return 0;
    }

    public static int[] filterOdd(int[] m) {
        return null;
    }
}
