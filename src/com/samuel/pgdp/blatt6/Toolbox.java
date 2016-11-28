package com.samuel.pgdp.blatt6;

import com.samuel.pgdp.MJAutoscreen;

public class Toolbox extends MJAutoscreen {

    public static void main(String[] args) {
        System.out.println(evenSum(-8));
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
        return 0;
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
