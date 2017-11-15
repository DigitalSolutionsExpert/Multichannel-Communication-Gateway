package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars;

import com.digitalsolutionsexpert.CustomerNotification.Service.Template.BaseTemplateServiceException;

public class HandlebarsTemplateServiceException extends BaseTemplateServiceException {
    public HandlebarsTemplateServiceException() {
    }

    public HandlebarsTemplateServiceException(String message) {
        super(message);
    }

    public HandlebarsTemplateServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlebarsTemplateServiceException(Throwable cause) {
        super(cause);
    }

    public HandlebarsTemplateServiceException(boolean definitive) {
        super(definitive);
    }

    public HandlebarsTemplateServiceException(String message, boolean definitive) {
        super(message, definitive);
    }

    public HandlebarsTemplateServiceException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public HandlebarsTemplateServiceException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
