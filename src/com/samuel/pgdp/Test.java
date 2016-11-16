package com.samuel.pgdp;

/**
 * Created by Samuel on 07.11.2016.
 */
public class Test {

    public static void main(String[] args) {
        test("0");
        test("1");
    }

    private static void test(String s) {
        System.out.println(s);
        test(s + "0");
        test(s + "1");
    }
}
