package com.wesley.java.concurrency.examples;

import java.util.concurrent.Executor;

/**
 * Executor is an interface that represents an object that executes provided tasks.
 */
public class ExecutorExample {

    public void run() {
        Executor executor = command -> {
            System.out.println("Running executor");
            command.run();
        };

        executor.execute(() -> {
            System.out.println("Making clay..");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Done");
        });
    }

    public static void main(String[] args) {
        ExecutorExample executorExample = new ExecutorExample();
        executorExample.run();
    }

}
