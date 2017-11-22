package com.digitalsolutionsexpert.CustomerNotification.Service.Monitor;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseThreadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class MonitorService extends BaseThreadedService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public MonitorService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }


    @Override
    public Object getMonitorData() {
        return null;
    }
}
