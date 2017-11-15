package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Persistence.BasePersistenceService;
import com.digitalsolutionsexpert.CustomerNotification.Service.Persistence.PersistenceServiceException;

import java.util.Map;

public abstract class BaseMailTemplateConfigurationPersistenceService extends BasePersistenceService {

    public BaseMailTemplateConfigurationPersistenceService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

    public abstract Map<Number, MailTemplate> retrieveMailTemplates() throws PersistenceServiceException;
}
