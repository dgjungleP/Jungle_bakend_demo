package com.jungle.jmx.simple;


import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationBroadcasterSupport;

public class Hello extends NotificationBroadcasterSupport implements HelloMBean {
    public static final int DEFAULT_CACHE_SIZE = 200;
    public final String name = "Jungle";
    private int cacheSize = DEFAULT_CACHE_SIZE;
    private int sequenceNumber = 1;

    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }

    @Override
    public int add(int x, int y) {
        return x + y;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCacheSize() {
        return this.cacheSize;
    }

    @Override
    public synchronized void setCacheSize(int size) {
        int oldSize = this.cacheSize;


        this.cacheSize = size;
        System.out.println("Cache size now is " + this.cacheSize);

        // the template to use notification
        AttributeChangeNotification notification = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(), "Cache size changed"
                , "CacheSize", "int", oldSize, this.cacheSize);
        sendNotification(notification);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = {
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        String className = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, className, description);

        return new MBeanNotificationInfo[]{info};
    }
}
