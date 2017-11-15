package com.digitalsolutionsexpert.CustomerNotification.Service.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;

public class PersistenceServiceException extends BaseServiceException {
    public PersistenceServiceException() {
    }

    public PersistenceServiceException(String message) {
        super(message);
    }

    public PersistenceServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceServiceException(Throwable cause) {
        super(cause);
    }

    public PersistenceServiceException(boolean definitive) {
        super(definitive);
    }

    public PersistenceServiceException(String message, boolean definitive) {
        super(message, definitive);
    }

    public PersistenceServiceException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public PersistenceServiceException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
