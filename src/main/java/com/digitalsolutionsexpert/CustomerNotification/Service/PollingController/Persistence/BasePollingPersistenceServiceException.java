package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;

public class BasePollingPersistenceServiceException extends BaseServiceException {
    public BasePollingPersistenceServiceException() {
    }

    public BasePollingPersistenceServiceException(String message) {
        super(message);
    }

    public BasePollingPersistenceServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasePollingPersistenceServiceException(Throwable cause) {
        super(cause);
    }

    public BasePollingPersistenceServiceException(boolean definitive) {
        super(definitive);
    }

    public BasePollingPersistenceServiceException(String message, boolean definitive) {
        super(message, definitive);
    }

    public BasePollingPersistenceServiceException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public BasePollingPersistenceServiceException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
