package com.samuel.pgdp.blatt8;

import com.samuel.pgdp.MiniJava;

import java.util.Arrays;
import java.util.HashMap;

public class MatrixMultOptMemoization extends MiniJava {

    public HashMap<Integer, Integer> cachedValues = new HashMap<>(500);
    public int counter = 0;

    public static void main(String[] args) {
        MatrixMultOptMemoization memo = new MatrixMultOptMemoization();
        System.out.println(memo.f(new int[][]{{10, 30}, {30, 5}, {5, 60}, {30, 5}, {10, 4}}));
        System.out.println("\n" + memo.cachedValues);
        System.out.println(memo.counter + " calls of method f");
    }

    public int f(int[][] mm) {
        return f(mm, 0, mm.length - 1);
    }

    /**
     * Recursively calculates the minimum amount of multiplication operations that are needed for a matrix multiplication of multiple matrices using {@code min}. If a value for f(mm, i, j) has already been calculated, the method takes the result from {@code cachedValues} to avoid having more method calls of {@code f} than necessary
     *
     * @param mm see min(int[][], int, int)
     * @param i  see min(int[][], int, int)
     * @param j  see min(int[][], int, int)
     * @return the lowest number of multiplications for this problem
     */
    public int f(int[][] mm, int i, int j) {
        counter++;
        //stop if i == j
        if (i >= j) return 0;
        else {
            int key, f;
            key = 10 * i + j;

            //check if we have cached this value
            if (cachedValues.containsKey(key)) {
                f = cachedValues.get(key);
                System.out.println("USING cached value " + f + " for key " + key);
                return cachedValues.get(key);
            } else {
                f = min(mm, i, i, j, 0);
                cachedValues.put(key, f);
                System.out.println("Cached value " + f + " for key " + key);
                return f;
            }
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
    @SuppressWarnings("Duplicates")
    public int min(int[][] mm, int i, int x, int j, int currMin) {
        if (x >= j) return currMin;
        else {
            //calculate the current formula value for x
            int curr;

            //now calculate
            curr = f(mm, i, x) + f(mm, x + 1, j) + (d1(mm[i]) * d2(mm[x]) * d2(mm[j]));

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
    public int d1(int[] m) {
        return m[0];
    }

    /**
     * Trivial helper method for maintaining the given formula structure
     *
     * @param m an array containing the parameters n and m of a matrix (size)
     * @return {@code m[1]}
     */
    public int d2(int[] m) {
        return m[1];
    }

    private class Input {
        private int[][] mm;
        private int i, j;

        public Input(int[][] mm, int i, int j) {
            this.mm = mm;
            this.i = i;
            this.j = j;
        }

        @Override
        public int hashCode() {
            String hash = "";

            //get the hash for the matrix
            hash += Arrays.deepHashCode(mm);

            //now add i and j and we're done
            hash += i;
            hash += j;

            return Integer.parseInt(hash);
        }

        @Override
        public boolean equals(Object obj) {
            return ((Input) obj).hashCode() == hashCode();
        }

        @Override
        public String toString() {
            return Arrays.deepToString(mm) + " " + i + " " + j;
        }
    }
}
