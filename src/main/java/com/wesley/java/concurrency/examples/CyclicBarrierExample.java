package com.wesley.java.concurrency.examples;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * CyclicBarrier works almost the same as CountDownLatch except that we can reuse it. Unlike CountDownLatch,
 * it allows multiple threads to wait for each other using await() method(known as barrier condition) before invoking the final task.
 */
public class CyclicBarrierExample {

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void run() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            System.out.println("Last worker has completed their work.");
            executorService.shutdown();
        });

        IntStream.rangeClosed(1, 10).forEach(i -> executorService.execute(() -> {
            System.out.println("Worker #" + i + " done, waiting for others to complete..");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }));
    }

    public static void main(String[] args) {
        CyclicBarrierExample cyclicBarrierExample = new CyclicBarrierExample();
        cyclicBarrierExample.run();
    }
}
