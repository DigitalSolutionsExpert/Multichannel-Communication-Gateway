package com.digitalsolutionsexpert.CustomerNotification.Service.Template;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseThreadedService;

public abstract class BaseTemplateService extends BaseThreadedService {

    public BaseTemplateService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

    public abstract Object apply(Number templateId, Object payload) throws BaseTemplateServiceException;
}
