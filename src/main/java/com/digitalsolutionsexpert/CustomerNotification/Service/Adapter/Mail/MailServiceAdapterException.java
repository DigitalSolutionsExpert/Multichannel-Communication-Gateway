package com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail;

import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.BaseServiceAdapterException;

public class MailServiceAdapterException extends BaseServiceAdapterException {
    public MailServiceAdapterException() {
    }

    public MailServiceAdapterException(String message) {
        super(message);
    }

    public MailServiceAdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailServiceAdapterException(Throwable cause) {
        super(cause);
    }

    public MailServiceAdapterException(boolean definitive) {
        super(definitive);
    }

    public MailServiceAdapterException(String message, boolean definitive) {
        super(message, definitive);
    }

    public MailServiceAdapterException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public MailServiceAdapterException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
