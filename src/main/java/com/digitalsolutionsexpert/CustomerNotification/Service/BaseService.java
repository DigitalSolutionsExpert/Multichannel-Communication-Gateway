package com.digitalsolutionsexpert.CustomerNotification.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;

@JsonIgnoreProperties({"applicationConfiguration"})
public abstract class BaseService {
    public static String SERVICE_BASE_PATH = "service";
    public static String SERVICE_CLASS_NAME_PROPERTY = "class-name";
    private ApplicationConfiguration applicationConfiguration;
    private BaseServiceInfo baseInfo;


    public BaseService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        this.applicationConfiguration = applicationConfiguration;
        this.baseInfo = BaseServiceInfo.create(applicationConfiguration, path, name);

        if (true || this.baseInfo.isGlobal()) {
            this.getApplicationConfiguration().registerService(this);
        }
    }

    public Object getMonitorData() throws MonitorDataServiceException {
        String monitorData = null;
        ObjectMapper objectMapper = null;
        try {
            objectMapper = new ObjectMapper();
            monitorData = objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MonitorDataServiceException(e);
        } finally {
            objectMapper = null;
        }
        return monitorData;
    }

    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    public ConfigurationProperties getProperties() {
        return this.baseInfo.getProperties();
    }

    public String getPath() {
        return this.baseInfo.getPath();
    }

    public String getName() {
        return this.baseInfo.getName();
    }

    public String getPathName(){
        return this.baseInfo.getFullPathName();
    }
}
