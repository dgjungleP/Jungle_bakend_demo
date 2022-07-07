package com.jungle.jmx.mxbean;

import javax.management.MXBean;

@MXBean
public interface QueueSamplerMXBean {
    QueueSample getQueueSample();

    void cleanQueue();
}
