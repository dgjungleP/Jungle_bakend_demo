package com.jungle;

import org.omg.IOP.ComponentIdHelper;

public class Student {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void readBook() {
        System.out.println("Reading book now!");
    }

    public void eat() {
        System.out.println("Eating now!");
    }
}
