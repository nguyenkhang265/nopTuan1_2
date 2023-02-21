package com.fit.se;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Exercise15 {
    public static void main(String[] args) {
        Task15 task = new Task15(13);
        FutureTask<Integer> futureTask = new FutureTask<>(task);

        Thread thread = new Thread(futureTask);
        thread.start();
        while(!futureTask.isDone()) {
            System.out.println("Waiting...");
        }

        try {
            System.out.println("Result: " + futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task15 implements Callable<Integer> {

    private int n;

    public Task15(int n) {
        this.n = n;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        //to check whether x is a prime number  (x is assumed to be greater than 1).
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                count++;
            }
        }

        return count;
    }

    private boolean isPrime(int i) {
        if(i < 2) return false;

        for (int j = 2; j < i; j++) {
            if (i % j == 0) {
                return false;
            }
        }
        return true;
    }
}