package com.digitalsolutionsexpert.CustomerNotification.Service.Monitor;


import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;

public class MonitorException extends BaseServiceException{
    public MonitorException() {
        super();
    }

    public MonitorException(String message) {
        super(message);
    }

    public MonitorException(String message, Throwable cause) {
        super(message, cause);
    }

    public MonitorException(Throwable cause) {
        super(cause);
    }

    public MonitorException(boolean definitive) {
        super(definitive);
    }

    public MonitorException(String message, boolean definitive) {
        super(message, definitive);
    }

    public MonitorException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public MonitorException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
