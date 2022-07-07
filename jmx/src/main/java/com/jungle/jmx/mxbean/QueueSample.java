package com.jungle.jmx.mxbean;

import javax.management.openmbean.CompositeData;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Date;

public class QueueSample {
    private final Date date;
    private final int size;
    private final String head;

    /**
     * 为了和CompositeData进行相互转换
     *
     * @see CompositeData
     */
    @ConstructorProperties({"date", "size", "head"})
    public QueueSample(Date date, int size, String head) {
        this.date = date;
        this.size = size;
        this.head = head;
    }

    public Date getDate() {
        return date;
    }

    public int getSize() {
        return size;
    }

    public String getHead() {
        return head;
    }
}
