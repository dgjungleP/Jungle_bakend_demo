package com.jungle.jmx.simple;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.concurrent.TimeUnit;

public class Application {


    public static void main(String[] args) throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

        // ${packageName}:${MBeanName}
        ObjectName name = new ObjectName("com.jungle.jmx.simple:type=Hello");
        Hello mBean = new Hello();
        platformMBeanServer.registerMBean(mBean, name);

        System.out.println("Waiting now!");

        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }


}
