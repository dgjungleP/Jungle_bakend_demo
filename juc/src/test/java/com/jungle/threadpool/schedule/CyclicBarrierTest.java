package com.jungle.threadpool.schedule;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CyclicBarrierTest {

    public static final int threadCount = 550;
    public static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(5);
    public static final CyclicBarrier RUNTIME_CYCLIC_BARRIER = new CyclicBarrier(5, () -> {
        System.out.println("This is a barrier break");
    });

    @Test
    public void simpleTest() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(300);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            TimeUnit.SECONDS.sleep(1);
            threadPool.execute(() -> {
                System.out.println("threadNum:" + threadNum + " is ready");
                try {
                    CYCLIC_BARRIER.await(60, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("threadNum:" + threadNum + " is finish");
            });
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {

        }
        System.out.println("Finish");
    }

    @Test
    public void runnerTest() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(300);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            TimeUnit.SECONDS.sleep(1);
            threadPool.execute(() -> {
                System.out.println("threadNum:" + threadNum + " is ready");
                try {
                    RUNTIME_CYCLIC_BARRIER.await(60, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("threadNum:" + threadNum + " is finish");
            });
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {

        }
        System.out.println("Finish");
    }

}
