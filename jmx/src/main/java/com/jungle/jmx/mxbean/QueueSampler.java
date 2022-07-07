package com.jungle.jmx.mxbean;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Queue;

public class QueueSampler implements QueueSamplerMXBean {
    private Queue<String> queue;

    public QueueSampler(Queue<String> queue) {
        this.queue = queue;
    }

    public QueueSampler() {
    }

    @Override
    public QueueSample getQueueSample() {
        return new QueueSample(LocalDateTime.now(), queue.size(), queue.peek());
    }

    @Override
    public void cleanQueue() {
        synchronized (queue) {
            queue.clear();
        }
    }
}
