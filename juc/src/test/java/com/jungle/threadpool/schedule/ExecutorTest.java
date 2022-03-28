package com.jungle.threadpool.schedule;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExecutorTest {
    public static final int CORE_POOL_SIZE = 5;

    public static final int MAX_POOL_SIZE = 10;
    public static final int QUEUE_CAPACITY = 100;
    public static final Long KEEP_ALIVE_TIME = 1L;

    private ThreadPoolExecutor executor;

    @BeforeAll
    public void prepare() {
        System.out.println("Hello");
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

    }

    @Test
    public void simpleScheduleTest() {
        for (int i = 0; i < 1000; i++) {
            Runnable myRunnableDemo = () -> {

                System.out.println(Thread.currentThread().getName() + " Start.Time:" + new Date());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " End.Time:" + new Date());
            };
            executor.execute(myRunnableDemo);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println("Finish all");

    }
}
