package com.jungle.threadpool.schedule;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static final int threadCount = 500;


    @Test
    public void simpleTest() {
        ExecutorService threadPool = Executors.newFixedThreadPool(300);

        Semaphore semaphore = new Semaphore(20);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("threadNum:" + threadNum);
                    TimeUnit.SECONDS.sleep(1);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
        }
        System.out.println("finish");
    }

    @Test
    public void fairTest() {
        ExecutorService threadPool = Executors.newFixedThreadPool(300);

        Semaphore semaphore = new Semaphore(20, true);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("threadNum:" + threadNum);
                    TimeUnit.SECONDS.sleep(1);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
        }
        System.out.println("finish");
    }
}
