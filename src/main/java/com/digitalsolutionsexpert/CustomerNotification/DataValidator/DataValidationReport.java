package com.digitalsolutionsexpert.CustomerNotification.DataValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class DataValidationReport {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
