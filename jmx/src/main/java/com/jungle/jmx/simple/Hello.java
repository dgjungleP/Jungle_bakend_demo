package com.jungle.jmx.simple;


public class Hello implements HelloMBean {
    public static final int DEFAULT_CACHE_SIZE = 200;
    public final String name = "Jungle";
    private int cacheSize = DEFAULT_CACHE_SIZE;

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
        this.cacheSize = size;
        System.out.println("Cache size now is " + this.cacheSize);
    }
}
