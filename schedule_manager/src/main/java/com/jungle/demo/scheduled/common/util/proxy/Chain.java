package com.jungle.demo.scheduled.common.util.proxy;

import lombok.Data;

import java.util.List;

@Data
public class Chain {
    private List<Point> points;
    private int index = -1;

    public Chain(List<Point> points) {
        this.points = points;
    }

    public int incIndex() {
        return ++index;
    }

    public void resetIndex() {
        this.index = -1;
    }
}
