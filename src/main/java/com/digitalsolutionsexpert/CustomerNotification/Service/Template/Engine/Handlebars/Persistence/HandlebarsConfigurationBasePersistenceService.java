package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Persistence.BasePersistenceService;
import com.digitalsolutionsexpert.CustomerNotification.Service.Persistence.PersistenceServiceException;


import java.util.Map;

public abstract class HandlebarsConfigurationBasePersistenceService extends BasePersistenceService {

    public HandlebarsConfigurationBasePersistenceService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

    public abstract Map<Number, HandlebarsTemplate> retrieveFormatTemplates() throws PersistenceServiceException;

}