package com.digitalsolutionsexpert.CustomerNotification.Service;

public class ServiceFactoryException extends BaseServiceException {
    public ServiceFactoryException() {
        super();
    }

    public ServiceFactoryException(String message) {
        super(message);
    }

    public ServiceFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceFactoryException(Throwable cause) {
        super(cause);
    }
}
