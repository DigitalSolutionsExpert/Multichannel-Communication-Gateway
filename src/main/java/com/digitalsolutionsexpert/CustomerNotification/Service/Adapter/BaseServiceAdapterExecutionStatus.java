package com.digitalsolutionsexpert.CustomerNotification.Service.Adapter;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceExecutionStatus;

import java.util.Date;

public class BaseServiceAdapterExecutionStatus extends BaseServiceExecutionStatus {

    public BaseServiceAdapterExecutionStatus() {
    }

    public BaseServiceAdapterExecutionStatus(String status, String statusReason, Date statusDate, boolean success, boolean definitive, String description) {
        super(status, statusReason, statusDate, success, definitive, description);
    }

    public static BaseServiceAdapterExecutionStatus createFrom(BaseServiceException baseServiceException) {
        if (baseServiceException == null) {
            return new BaseServiceAdapterExecutionStatus(
                    BaseServiceExecutionStatus.STATUS_SUCCESS,
                    null,
                    new Date(),
                    true,
                    true,
                    null);
        } else {
            return new BaseServiceAdapterExecutionStatus(
                    BaseServiceExecutionStatus.STATUS_EXCPEPTION,
                    baseServiceException.getClass().getSimpleName(),
                    new Date(),
                    false,
                    baseServiceException.isDefinitive(),
                    baseServiceException.getMessage()
            );
        }
    }

}
