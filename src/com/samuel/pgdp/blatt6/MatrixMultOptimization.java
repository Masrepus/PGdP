package com.samuel.pgdp.blatt6;

import com.samuel.pgdp.MiniJava;

public class MatrixMultOptimization extends MiniJava {

    public static int counter = 0;

    public static void main(String[] args) {
        System.out.println(f(new int[][]{{10, 30}, {30, 5}, {5, 60}, {30, 5}, {10, 4}}));
        System.out.println(counter);
    }

    public static int f(int[][] mm) {
        return f(mm, 0, mm.length - 1);
    }

    public static int f(int[][] mm, int i, int j) {
        counter++;
        //stop if i == j
        if (i >= j) return 0;
        else {
            return min(mm, i, i, j, 0);
        }
    }

    /**
     * Recursively calculates the minimum amount of multiplication operations that are needed for a matrix multiplication of multiple matrices
     *
     * @param mm      an array containing abstract array representations of the matrices to multiply: if a matrix is of the size n*m, add an array of {n,m} to {@code mm}
     * @param i       to determine the current position in {@code mm}
     * @param x       being incremented from i to j-1
     * @param j       to determine the current position in {@code mm}
     * @param currMin the currently lowest amount of multiplication operations for this matrix multiplication problem. Pass 0 at start
     * @return the lowest number of multiplications for this problem
     */
    public static int min(int[][] mm, int i, int x, int j, int currMin) {
        if (x >= j) return currMin;
        else {
            //calculate the current formula value for x
            int curr = f(mm, i, x) + f(mm, x + 1, j) + (d1(mm[i]) * d2(mm[x]) * d2(mm[j]));
            //check if this value is smaller than any value before or if this is the first value ever, then update the currMin value
            if (curr < currMin || currMin == 0) return min(mm, i, x + 1, j, curr);
            else return min(mm, i, x + 1, j, currMin);
        }
    }

    /**
     * Trivial helper method for maintaining the given formula structure
     *
     * @param m an array containing the parameters n and m of a matrix (size)
     * @return {@code m[0]}
     */
    public static int d1(int[] m) {
        return m[0];
    }

    /**
     * Trivial helper method for maintaining the given formula structure
     *
     * @param m an array containing the parameters n and m of a matrix (size)
     * @return {@code m[1]}
     */
    public static int d2(int[] m) {
        return m[1];
    }

}
