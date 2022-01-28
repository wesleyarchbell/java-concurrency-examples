package com.wesley.java.concurrency.examples;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch (introduced in JDK 5) is a utility class which blocks a set of threads until some operation completes.
 */
public class CountDownLatchExample {

    private final CountDownLatch countDownLatch = new CountDownLatch(5);

    public void run() throws InterruptedException {
        for (int i = 10; i > 0; i--) {
            System.out.println("Countdown #" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            countDownLatch.countDown();
        }
        if (countDownLatch.await(10, TimeUnit.SECONDS)) {
            System.out.println("Completed countdown");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchExample countDownLatchExample = new CountDownLatchExample();
        countDownLatchExample.run();
    }
}
