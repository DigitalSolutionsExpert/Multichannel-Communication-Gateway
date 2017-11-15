package com.digitalsolutionsexpert.CustomerNotification.Service;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;

import java.lang.reflect.Constructor;

public class ServiceFactory {
    public static ServiceFactory defaultServiceFactory = new ServiceFactory();

    public BaseService create(ApplicationConfiguration applicationConfiguration, String... servicePathNameOrName) throws ServiceFactoryException {
        BaseService service = null;
        BaseServiceInfo baseServiceInfo = null;
        String serviceFullPathName = BaseServiceInfo.getAddressedPathName(servicePathNameOrName);
        try {
            baseServiceInfo = BaseServiceInfo.create(applicationConfiguration, serviceFullPathName);
            String serviceClassName = (String) (baseServiceInfo.getProperties()).get(BaseService.SERVICE_CLASS_NAME_PROPERTY);
            Class<BaseService> clazz = (Class<BaseService>) Class.forName(serviceClassName);
            Constructor<BaseService> constructor = clazz.getDeclaredConstructor(ApplicationConfiguration.class, String.class, String.class);
            service = constructor.newInstance(applicationConfiguration, baseServiceInfo.getPath(), baseServiceInfo.getName());
            return service;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceFactoryException("Unable to create service intance for service address [" + serviceFullPathName + "].", e);
        }
    }
}
