package com.digitalsolutionsexpert.CustomerNotification.Schedule;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.UUID;


@JsonIgnoreProperties({"DEFAULT_ENABLED", "class-name", "className"})
public abstract class BaseSchedule implements Serializable {
    private static boolean DEFAULT_ENABLED = true;
    private String name;
    private boolean enabled;
    private long lastHappened;

    public BaseSchedule() {
        this.name = String.valueOf(UUID.randomUUID());
        this.setEnabled(DEFAULT_ENABLED);
        this.setLastHappened(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getLastHappened() {
        return this.lastHappened;
    }

    public void setLastHappened() {
        this.setLastHappened(System.currentTimeMillis());
    }

    public void setLastHappened(long lastHappened) {
        this.lastHappened = lastHappened;
    }

    public boolean isEligible() {
        return this.isEnabled();
    }
}
