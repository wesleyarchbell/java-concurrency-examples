package com.wesley.java.concurrency.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * The Semaphore is used for blocking thread level access to some part of the physical or logical resource.
 * A semaphore contains a set of permits; whenever a thread tries to enter the critical section, it needs to check the semaphore if a permit is available or not.
 */
public class SemaphoreExample {

    private final Semaphore semaphore = new Semaphore(10);
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    public void run() {

        System.out.println("Max number of ticket permits at a given time: " + semaphore.availablePermits());

        IntStream.rangeClosed(1, 100).forEach(i -> {
            executorService.execute(() -> {
                System.out.println("Person #" + i + " waiting to buy a ticket, queue length: " + semaphore.getQueueLength());
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Person #" + i + " buying a ticket.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Person #" + i + " bought a ticket.");
                semaphore.release();
            });
        });
        executorService.shutdown();
    }

    public static void main(String[] args) {
        SemaphoreExample semaphoreExample = new SemaphoreExample();
        semaphoreExample.run();
    }
}
