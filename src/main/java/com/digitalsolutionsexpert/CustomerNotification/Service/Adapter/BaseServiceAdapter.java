package com.digitalsolutionsexpert.CustomerNotification.Service.Adapter;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseThreadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public abstract class BaseServiceAdapter extends BaseThreadedService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public BaseServiceAdapter(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

    public abstract BaseServiceAdapterExecutionResult execute( BaseServiceAdapterRequest request);

    public abstract void onReceive(BaseServiceAdapterExecutionResult executionStatus);
}
