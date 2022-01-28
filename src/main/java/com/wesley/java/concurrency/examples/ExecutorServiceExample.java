package com.wesley.java.concurrency.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * ExecutorService is a complete solution for asynchronous processing. It manages an in-memory queue and schedules submitted tasks based on thread availability.
 */
public class ExecutorServiceExample {

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public void run() {
        IntStream.rangeClosed(1, 10).forEach(i -> executorService.submit(() -> {
            System.out.println("Making brick #" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Done making brick #" + i);
        }));
        try {
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        ExecutorServiceExample executorServiceExample = new ExecutorServiceExample();
        executorServiceExample.run();
    }
}
