package com.digitalsolutionsexpert.CustomerNotification.Service.Adapter;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;

public class BaseServiceAdapterException extends BaseServiceException {

    public BaseServiceAdapterException() {
        super();
    }

    public BaseServiceAdapterException(String message) {
        super(message);
    }

    public BaseServiceAdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseServiceAdapterException(Throwable cause) {
        super(cause);
    }

    public BaseServiceAdapterException(boolean definitive) {
        super(definitive);
    }

    public BaseServiceAdapterException(String message, boolean definitive) {
        super(message, definitive);
    }

    public BaseServiceAdapterException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public BaseServiceAdapterException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
