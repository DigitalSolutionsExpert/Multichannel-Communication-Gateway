package com.digitalsolutionsexpert.CustomerNotification.Service;

import java.util.Date;

public class BaseServiceExecutionStatus {

    public static final String STATUS_IN_PROCESS = "INPROC";
    public static final String STATUS_EXCPEPTION = "ERROR";
    public static final String STATUS_SUCCESS = "SUCCESS";

    private String status;
    private String statusReason;
    private Date statusDate;
    private boolean success;
    private boolean definitive;
    private String description;

    public BaseServiceExecutionStatus() {
    }


    public BaseServiceExecutionStatus(String status, String statusReason, Date statusDate, boolean success, boolean definitive, String description) {
        this.status = status;
        this.statusReason = statusReason;
        this.statusDate = statusDate;
        this.success = success;
        this.definitive = definitive;
        this.description = description;
    }

    public static BaseServiceExecutionStatus createFrom(BaseServiceException baseServiceException) {
        if (baseServiceException == null) {
            return new BaseServiceExecutionStatus(
                    BaseServiceExecutionStatus.STATUS_SUCCESS,
                    null,
                    new Date(),
                    true,
                    true,
                    null);
        } else {
            return new BaseServiceExecutionStatus(
                    BaseServiceExecutionStatus.STATUS_EXCPEPTION,
                    baseServiceException.getClass().getSimpleName(),
                    new Date(),
                    false,
                    baseServiceException.isDefinitive(),
                    baseServiceException.getMessage());
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isDefinitive() {
        return definitive;
    }

    public void setDefinitive(boolean definitive) {
        this.definitive = definitive;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
