package com.digitalsolutionsexpert.CustomerNotification.Service;

public class MonitorDataServiceException extends BaseServiceException {
    public MonitorDataServiceException() {
    }

    public MonitorDataServiceException(String message) {
        super(message);
    }

    public MonitorDataServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MonitorDataServiceException(Throwable cause) {
        super(cause);
    }

    public MonitorDataServiceException(boolean definitive) {
        super(definitive);
    }

    public MonitorDataServiceException(String message, boolean definitive) {
        super(message, definitive);
    }

    public MonitorDataServiceException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public MonitorDataServiceException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
