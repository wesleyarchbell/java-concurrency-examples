package com.wesley.java.concurrency.examples;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ScheduledExecutorService is a similar interface to ExecutorService, but it can perform tasks periodically.
 */
public class ScheduledExecutorServiceExample {

    private final AtomicInteger counter = new AtomicInteger();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    private void run() {

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int current = counter.incrementAndGet();
            System.out.println("Grinding stone #" + current);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Done griding stone #" + current);
            if (counter.get() == 10) {
                scheduledExecutorService.shutdown();
            }

        }, 1, 500, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        ScheduledExecutorServiceExample scheduledExecutorServiceExample = new ScheduledExecutorServiceExample();
        scheduledExecutorServiceExample.run();
    }
}
