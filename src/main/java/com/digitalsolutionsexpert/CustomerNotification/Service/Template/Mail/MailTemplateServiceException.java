package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail;

import com.digitalsolutionsexpert.CustomerNotification.Service.Template.BaseTemplateServiceException;

public class MailTemplateServiceException extends BaseTemplateServiceException {
    public MailTemplateServiceException() {
    }

    public MailTemplateServiceException(String message) {
        super(message);
    }

    public MailTemplateServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailTemplateServiceException(Throwable cause) {
        super(cause);
    }

    public MailTemplateServiceException(boolean definitive) {
        super(definitive);
    }

    public MailTemplateServiceException(String message, boolean definitive) {
        super(message, definitive);
    }

    public MailTemplateServiceException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public MailTemplateServiceException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
