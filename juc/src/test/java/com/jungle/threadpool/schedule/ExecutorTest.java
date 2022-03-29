package com.jungle.threadpool.schedule;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

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
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
                ThreadFactoryBuilder.create().setNamePrefix("Image-").setDaemon(false).build());
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println("=================================");
            System.out.println("Thread size:" + executor.getPoolSize());
            System.out.println("Active Thread size:" + executor.getActiveCount());
            System.out.println("Number of Tasks:" + executor.getCompletedTaskCount());
            System.out.println("Number of Tasks in Queue:" + executor.getQueue().size());
            System.out.println("=================================");
        }, 0, 1, TimeUnit.SECONDS);

    }

    @Test
    public void simpleScheduleTest() {
        for (int i = 0; i < 100; i++) {
            Runnable myRunnableDemo = () -> {

                System.out.println(Thread.currentThread().getName() + " Start.Time:" + new Date());
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
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
