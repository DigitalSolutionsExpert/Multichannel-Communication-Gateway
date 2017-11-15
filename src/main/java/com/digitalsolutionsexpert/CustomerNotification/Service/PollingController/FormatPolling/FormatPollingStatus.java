package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceExecutionStatus;

import java.util.Date;

public class FormatPollingStatus extends BaseServiceExecutionStatus {
    public FormatPollingStatus() {
    }

    public FormatPollingStatus(String status, String statusReason, Date statusDate, boolean success, boolean definitive, String description) {
        super(status, statusReason, statusDate, success, definitive, description);
    }
}
