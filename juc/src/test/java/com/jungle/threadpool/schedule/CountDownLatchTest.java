package com.jungle.threadpool.schedule;

import cn.hutool.core.collection.SpliteratorUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

    public static final int threadCount = 550;

    @Test
    public void simpleTest() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(300);

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {

            final int threadNum = i;
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("ThreadNum:" + threadNum);
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("Finish");
    }

}
