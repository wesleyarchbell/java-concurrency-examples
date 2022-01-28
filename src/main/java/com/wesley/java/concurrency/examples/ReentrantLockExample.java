package com.wesley.java.concurrency.examples;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Simply put, a lock is a more flexible and sophisticated thread synchronization mechanism than the standard synchronized block.
 * ReentrantLock is a mutual exclusive lock, similar to implicit locking provided by synchronized keyword in Java,
 * with extended features like fairness, which can be used to provide lock to longest waiting thread.
 */
public class ReentrantLockExample {

    private final Lock lock = new ReentrantLock();

    public void run() {
        IntStream.rangeClosed(1, 100).forEach(i -> new Thread(() -> performTask(i)).start());
    }

    public void performTask(int count) {
        lock.lock();
        try {
            // The code below is now thread safe
            System.out.println("Performing task #" + count);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockExample reentrantLockExample = new ReentrantLockExample();
        reentrantLockExample.run();
    }

}
