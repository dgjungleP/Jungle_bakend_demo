package com.jungle.jmx.mxbean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName name = new ObjectName("com.jungle.jmx.mxbean:type=QueueSampler");
        Queue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("Request-1");
        queue.add("Request-2");
        queue.add("Request-3");
        queue.add("Request-4");
        QueueSampler sampler = new QueueSampler(queue);

        platformMBeanServer.registerMBean(sampler, name);
        System.out.println("Waiting now!");
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
