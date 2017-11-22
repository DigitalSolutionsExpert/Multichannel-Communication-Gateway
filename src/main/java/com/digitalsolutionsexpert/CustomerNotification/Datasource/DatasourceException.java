package com.digitalsolutionsexpert.CustomerNotification.Datasource;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;

public class DatasourceException extends BaseServiceException{

    public DatasourceException() {
    }

    public DatasourceException(String message) {
        super(message);
    }

    public DatasourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatasourceException(Throwable cause) {
        super(cause);
    }

    public DatasourceException(boolean definitive) {
        super(definitive);
    }

    public DatasourceException(String message, boolean definitive) {
        super(message, definitive);
    }

    public DatasourceException(String message, Throwable cause, boolean definitive) {
        super(message, cause, definitive);
    }

    public DatasourceException(Throwable cause, boolean definitive) {
        super(cause, definitive);
    }
}
