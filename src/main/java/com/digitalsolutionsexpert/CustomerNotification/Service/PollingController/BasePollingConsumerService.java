package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseService;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;

public abstract class BasePollingConsumerService<REQUEST, RESPONSE, STATUS> extends BaseService implements Runnable {
    private BasePollingControllerService pollingControllerService;
    private REQUEST request;

    public BasePollingConsumerService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

    public BasePollingControllerService getPollingControllerService() {
        return pollingControllerService;
    }

    public void setPollingControllerService(BasePollingControllerService pollingControllerService) {
        this.pollingControllerService = pollingControllerService;
    }

    public REQUEST getRequest() {
        return request;
    }

    public void setRequest(REQUEST request) {
        this.request = request;
    }
}
