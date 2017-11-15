package com.digitalsolutionsexpert.CustomerNotification.Service;

import com.digitalsolutionsexpert.CustomerNotification.Schedule.BaseSchedule;

public abstract class BaseServiceScheduleListener {

    public BaseServiceScheduleListener() {
    }

    public abstract void onSchedule(final BaseSchedule schedule);
}
