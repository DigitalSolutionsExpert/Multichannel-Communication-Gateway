package com.digitalsolutionsexpert.CustomerNotification.Service.Template;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;

public class BaseTemplateServiceException extends BaseServiceException {
    public BaseTemplateServiceException() {
    }

    public BaseTemplateServiceException(String message) {
        super(message);
    }

    public BaseTemplateServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseTemplateServiceException(Throwable cause) {
        super(cause);
    }

    public BaseTemplateServiceException(boolean definitive) {
        super(definitive);
    }

    public BaseTemplateServiceException(String message, boolean definitive) {
        super(message, definitive);
    }

    public BaseTemplateServiceException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public BaseTemplateServiceException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
