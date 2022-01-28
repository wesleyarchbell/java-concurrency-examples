package com.wesley.java.concurrency.examples;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Future is used to represent the result of an asynchronous operation.
 */
public class FutureExample {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private void run() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("Calculating the answer to life, the universe and everything...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 42;
        });

        if (!future.isDone()) {
            System.out.println("Still calculating answer...");
        }

        Integer answer = future.get(10, TimeUnit.SECONDS);
        System.out.println("The answer to life, the universe and everything is " + answer + "!");
        executorService.shutdown();
    }

    public static void main(String[] args) throws Exception {
        FutureExample futureExample = new FutureExample();
        futureExample.run();
    }
}
