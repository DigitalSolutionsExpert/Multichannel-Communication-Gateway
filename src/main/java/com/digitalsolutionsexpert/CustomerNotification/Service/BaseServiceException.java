package com.digitalsolutionsexpert.CustomerNotification.Service;

public class BaseServiceException extends Exception {
    private boolean definitive;

    public BaseServiceException() {
        this(true);
    }

    public BaseServiceException(String message) {
        this(message, true);
    }

    public BaseServiceException(String message, Throwable cause) {
        this(message, cause, true);
    }

    public BaseServiceException(Throwable cause) {
        this(cause, true);
    }

    public BaseServiceException(boolean definitive) {
        super();
        this.definitive = definitive;
    }

    public BaseServiceException(String message, boolean definitive) {
        super(message);
        this.definitive = definitive;
    }

    public BaseServiceException(String message, Throwable cause, boolean definitive) {
        super(message, cause);
        this.definitive = definitive;
    }

    public BaseServiceException(Throwable cause, boolean definitive) {
        super(cause);
        this.definitive = definitive;
    }

    public boolean isDefinitive() {
        return definitive;
    }
}
