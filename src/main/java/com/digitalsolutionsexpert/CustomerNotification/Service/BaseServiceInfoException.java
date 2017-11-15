package com.digitalsolutionsexpert.CustomerNotification.Service;

public class BaseServiceInfoException extends BaseServiceException {
    public BaseServiceInfoException() {
    }

    public BaseServiceInfoException(String message) {
        super(message);
    }

    public BaseServiceInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseServiceInfoException(Throwable cause) {
        super(cause);
    }

    public BaseServiceInfoException(boolean definitive) {
        super(definitive);
    }

    public BaseServiceInfoException(String message, boolean definitive) {
        super(message, definitive);
    }

    public BaseServiceInfoException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public BaseServiceInfoException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
