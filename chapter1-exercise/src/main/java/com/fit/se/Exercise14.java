package com.fit.se;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Exercise14 {
    public static void main(String[] args) throws InterruptedException {

        // Callable<Integer> task1 = new Exer14Task(1, 1000);
        // FutureTask<Integer> futureTask1 = new FutureTask<>(task1);
        // Thread thread1 = new Thread(futureTask1);
        // thread1.start();

        // while(!futureTask1.isDone()) {
        //     System.out.println("Thread 1 is running...");
        // }

        // try {
        //     System.out.println("Sum = " + futureTask1.get());
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

       List<Callable<Integer>> tasks = List.of(
           new Exer14Task(1, 100),
           new Exer14Task(101, 200),
           new Exer14Task(201, 300),
           new Exer14Task(301, 400),
           new Exer14Task(401, 500),
           new Exer14Task(501, 600),
           new Exer14Task(601, 700),
           new Exer14Task(701, 800),
           new Exer14Task(801, 900),
           new Exer14Task(901, 1000)
       );

    ExecutorService executorService = Executors.newFixedThreadPool(5);
    List<Future<Integer>> fs = executorService.invokeAll(tasks);
    executorService.shutdown();
    while(!executorService.isTerminated()) {
        System.out.println("Waiting for all threads to finish...");
    }
    System.out.println("All threads finished!");
    int sum = 0;
    for (Future<Integer> f : fs) {
        try {
            sum += f.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    System.out.println("Sum = " + sum);
    }
}

class Exer14Task implements Callable<Integer> {

    private int a;
    private int b;

    public Exer14Task(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = a; i <= b; i++) {
            sum += i;
        }
        return sum;
    }
}
