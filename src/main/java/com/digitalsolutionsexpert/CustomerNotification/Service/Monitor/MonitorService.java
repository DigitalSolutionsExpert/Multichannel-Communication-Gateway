package com.digitalsolutionsexpert.CustomerNotification.Service.Monitor;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseThreadedService;

public class MonitorService extends BaseThreadedService {

    public MonitorService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }


    @Override
    public Object getMonitorData() {
        return null;
    }
}
