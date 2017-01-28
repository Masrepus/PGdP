package com.samuel.pgdp.blatt12;

/**
 * Created by Samuel on 22.01.2017.
 */
public class Map {

    public static void main(String[] args) {
        try {
            map(new IntToString(), new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, new String[15], 7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static <T, R> void map(Fun<T, R> f, T[] a, R[] b, int n)
            throws InterruptedException {

        //check params
        if (a.length > b.length) throw new IllegalArgumentException("The length of R[] b must be greater than or equal to a.length");
        if (n > a.length) throw new IllegalArgumentException("n must be <= a.length");
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");

        Worker[] workers = new Worker[n];

        //we have k%n areas of size k/n + 1 and the rest is of size k/n
        int nrBiggerAreas = a.length % n;
        int sizeBiggerAreas = a.length/n + 1;
        int sizeOtherAreas = a.length/n;

        int nextStart = 0;

        for (int i = 0; i < workers.length; i++) {
            //check if this thread has to use the bigger area or not
            int currSize = nrBiggerAreas > 0 ? sizeBiggerAreas : sizeOtherAreas;

            workers[i] = new Worker<>(f, a, b, nextStart, nextStart + currSize - 1);
            workers[i].start();
            System.out.println("Worker " + i + " " + workers[i] + " started, size " + currSize + ", start: " + nextStart + ", end: " + (nextStart + currSize - 1));

            //update values
            if (nrBiggerAreas > 0) nrBiggerAreas--;
            nextStart += currSize;
        }

        for (Worker worker : workers) {
            worker.join();
        }

        for (R r : b) {
            System.out.println(r);
        }
    }

    private static class Worker<T, R> extends Thread {

        private Fun<T, R> f;
        private T[] a;
        private R[] b;
        private int start, end;

        public Worker(Fun<T, R> f, T[] a, R[] b, int start, int end) {
            this.f = f;
            this.a = a;
            this.b = b;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i <= end; i++) {
                b[i] = f.apply(a[i]);
            }
            System.out.println(this + " done");
        }
    }
}
