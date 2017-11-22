package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseThreadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class BasePollingPersistenceService<REQUEST, RESPONSE, STATUS> extends BaseThreadedService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private BasePollingPersistenceService persistenceService;
    public BasePollingPersistenceService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(applicationConfiguration, path, name);

    }

    public abstract List<REQUEST> poll(int size, String serviceName) throws BasePollingPersistenceServiceException;

    public abstract void updateStatus(REQUEST request, RESPONSE response, STATUS status) throws BasePollingPersistenceServiceException;

    public abstract void moveToHistory(int size) throws BasePollingPersistenceServiceException;
}
