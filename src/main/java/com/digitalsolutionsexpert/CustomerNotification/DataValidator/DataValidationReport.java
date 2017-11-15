package com.digitalsolutionsexpert.CustomerNotification.DataValidator;

import java.util.List;

public class DataValidationReport {
    private boolean success;
    private List<String> messageList;

    public DataValidationReport(boolean success, List<String> messageList) {
        this.success = success;
        this.messageList = messageList;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public List<String> getMessageList() {
        return this.messageList;
    }
}
