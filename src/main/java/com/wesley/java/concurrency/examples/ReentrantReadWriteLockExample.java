package com.wesley.java.concurrency.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * A ReadWriteLock is designed as a high-level locking mechanism that allows you to add thread-safety feature to a
 * data structure while increasing throughput by allowing multiple threads to read the data concurrently and one thread to update the data exclusively.
 */
public class ReentrantReadWriteLockExample {

    // Use the read-write lock to make this list thread-safe
    private final List<Integer> threadSafeList = new ArrayList<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void run() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            new Thread(this::addRandom).start();
        });

        IntStream.rangeClosed(1, 100).forEach(i -> new Thread(() -> {
            Integer value = getRandom();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Got random value: " + value);
        }).start());
    }

    public void addRandom() {
        Lock lock = this.lock.writeLock();
        lock.lock();
        try {
            Random random = new Random();
            int number = random.nextInt(100);
            threadSafeList.add(number);
            System.out.println("Added random value: " + number);
        } finally {
            lock.unlock();
        }
    }

    public Integer getRandom() {
        Lock lock = this.lock.readLock();
        lock.lock();
        try {
            Random random = new Random();
            int number = random.nextInt(threadSafeList.size());
            return threadSafeList.get(number);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockExample reentrantReadWriteLockExample = new ReentrantReadWriteLockExample();
        reentrantReadWriteLockExample.run();
    }
}
