package com.digitalsolutionsexpert.CustomerNotification.Schedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class IntervalSchedule extends BaseSchedule {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static long DEFAULT_INTERVAL = 1000;
    private static long DEFAULT_DELAY = 0;

    private long interval;
    private long delay;

    public IntervalSchedule() {
        super();
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.setLastHappened(System.currentTimeMillis() + delay);
        this.delay = delay;
    }

    @Override
    public boolean isEligible() {
        return super.isEligible() && ((System.currentTimeMillis() - this.getLastHappened()) > interval);
    }
}
