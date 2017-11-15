package com.digitalsolutionsexpert.CustomerNotification.Schedule;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;

public class ScheduleException extends BaseServiceException {
    public ScheduleException() {
    }

    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleException(Throwable cause) {
        super(cause);
    }
}
