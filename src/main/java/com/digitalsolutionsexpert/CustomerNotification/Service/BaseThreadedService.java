package com.digitalsolutionsexpert.CustomerNotification.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.BaseSchedule;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.ScheduleException;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.ScheduleFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@JsonIgnoreProperties({"thread"})
public abstract class BaseThreadedService extends BaseService implements Runnable {
    private Thread thread;
    private Map<String, BaseSchedule> schedules;
    private Map<String, List<BaseServiceScheduleListener>> scheduleListeners;
    private int sleepInterval;
    private int maxIterations;
    private boolean running;
    private boolean requestedStop;


    public BaseThreadedService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
        this.thread = null;
        this.schedules = null;
        this.scheduleListeners = null;
        ConfigurationProperties threadProperties = this.getProperties().getConfigurationSubtree("thread");
        if (threadProperties == null) {
            threadProperties = new ConfigurationProperties();
        }
        this.sleepInterval = (Integer) threadProperties.get("sleep", 1000);
        this.maxIterations = (Integer) threadProperties.get("max-iterations", -1);

        if (threadProperties.containsKey("schedule")) {
            ScheduleFactory scheduleFactory = new ScheduleFactory();
            BaseSchedule[] baseScheduleArray = scheduleFactory.createArray(threadProperties.getConfigurationSubtree("schedule"));
            this.scheduleListeners = new ConcurrentHashMap<>();
            for (int i = 0; i < baseScheduleArray.length; i++) {
                this.getSchedules().put(baseScheduleArray[i].getName(), baseScheduleArray[i]);
            }
        }
        this.running = false;
        this.requestedStop = false;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            this.requestStop();
            this.thread = null;
        } finally {
            super.finalize();
        }
    }

    public Set<String> getScheduleNames() {
        return this.getSchedules().keySet();
    }

    public Map<String, BaseSchedule> getSchedules() {
        if (this.schedules == null) {
            this.schedules = new ConcurrentHashMap<>();
        }
        return this.schedules;
    }

    public synchronized void addScheduleListener(String scheduleName, BaseServiceScheduleListener scheduleListener) throws ScheduleException {
        if (this.scheduleListeners == null) {
            this.scheduleListeners = new ConcurrentHashMap<>();
        }
        if (!this.getSchedules().containsKey(scheduleName)) {
            throw new ScheduleException("The schedule with name [" + scheduleName + "] does not exist.");
        } else {
            if (!this.scheduleListeners.containsKey(scheduleName)) {
                this.scheduleListeners.put(scheduleName, new ArrayList<BaseServiceScheduleListener>());
            }
            this.scheduleListeners.get(scheduleName).add(scheduleListener);

        }
    }

    public synchronized void removeScheduleListener(BaseServiceScheduleListener scheduleListener) {

    }

    private synchronized void _fireScheduleEvents() {
        Iterator scheduleIterator = this.getScheduleNames().iterator();
        while (scheduleIterator.hasNext()) {
            String scheduleName = (String) scheduleIterator.next();
            if (this.scheduleListeners.containsKey(scheduleName) && this.getSchedules().get(scheduleName).isEligible()) {
                Iterator scheduleListenerIterator = this.scheduleListeners.get(scheduleName).iterator();
                while (scheduleListenerIterator.hasNext()) {
                    BaseServiceScheduleListener scheduleListener = (BaseServiceScheduleListener) scheduleListenerIterator.next();
                    try {
                        scheduleListener.onSchedule(this.getSchedules().get(scheduleName));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.schedules.get(scheduleName).setLastHappened();
            }
        }
    }

    public synchronized boolean isRunning() {
        return this.running;
    }

    private synchronized void setRunning(boolean running) {
        this.running = running;
    }

    public synchronized Thread requestStart() {
        if (this.thread == null) {
            this.thread = new Thread(this);
        }
        if (this.running) {
            throw new IllegalThreadStateException("The thread [" + this.thread.toString() + "] associated to the service [" + this.getName() + "] is currently running.");
        }
        this.requestedStop = false;
        if (this.getProperties().containsKey("thread")) {
            this.maxIterations = Integer.valueOf(String.valueOf(this.getProperties().getConfigurationSubtree("thread").get("max-iterations", -1)));
        } else {
            this.maxIterations = -1;
        }
        this.thread.start();

        return this.thread;
    }

    public synchronized void requestStop() {
        if (!this.running) {
            throw new IllegalThreadStateException("The thread [" + this.thread.toString() + "] associated to the service [" + this.getName() + "] is not running.");
        }
        this.requestedStop = true;
    }

    public Thread getThread() {
        return thread;
    }

    public void run() {
        this.running = true;
        if (this.thread == null) {
            this.thread = Thread.currentThread();
        }
        //loop for executing iterations
        while (!this.requestedStop && maxIterations != 0) {
            //sleep on each iteration
            try {
                Thread.sleep(this.sleepInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this._fireScheduleEvents();

            if (this.maxIterations > 0) {
                this.maxIterations--;
            }
        }
        this.running = false;
    }
}
