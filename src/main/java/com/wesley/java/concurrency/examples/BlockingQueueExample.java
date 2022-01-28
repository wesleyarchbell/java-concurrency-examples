package com.wesley.java.concurrency.examples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * In asynchronous programming, one of the most common integration patterns is the producer-consumer pattern.
 * The java.util.concurrent package comes with a data-structure know as BlockingQueue – which can be very useful in these async scenarios.
 */
public class BlockingQueueExample {

    private final BlockingQueue<String> boundedBlockingQueue = new LinkedBlockingQueue<>(5);

    public void run() {

        Runnable producer = () -> IntStream.rangeClosed(1, 100).forEach(i -> {
            try {
                System.out.println("Task #" + i + " added to queue");
                boundedBlockingQueue.put("Task" + i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Runnable consumer = () -> {
            while (true) {
                try {
                    String task = boundedBlockingQueue.take();
                    System.out.println("Retrieved " + task + "");
                    if ("Task100".equals(task)) {
                        break;
                    }
                } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
                }
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }

    public static void main(String[] args) {
        BlockingQueueExample blockingQueueExample = new BlockingQueueExample();
        blockingQueueExample.run();
    }

}
