package com.digitalsolutionsexpert.CustomerNotification.Service.Adapter;

public class BaseServiceAdapterExecutionResult {

    private BaseServiceAdapterResponse response;
    private BaseServiceAdapterExecutionStatus status;

    public BaseServiceAdapterExecutionResult(BaseServiceAdapterResponse response, BaseServiceAdapterExecutionStatus status) {
        this.response = response;
        this.status = status;
    }

    public BaseServiceAdapterResponse getResponse() {
        return response;
    }

    public BaseServiceAdapterExecutionStatus getStatus() {
        return status;
    }
}
