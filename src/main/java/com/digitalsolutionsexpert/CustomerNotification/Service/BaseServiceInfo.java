package com.digitalsolutionsexpert.CustomerNotification.Service;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Objects;

public class BaseServiceInfo {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String path;
    private String name;
    private ConfigurationProperties properties;

    public BaseServiceInfo() {
    }

    public static String getAddressedPathName(String... servicePathNameOrName) {
        String serviceFullPathName = "";

        for (int i = 0; i < servicePathNameOrName.length; i++) {
            serviceFullPathName = serviceFullPathName + (serviceFullPathName.length() > 0 ? ConfigurationProperties.PATH_DELIMITER : "") + servicePathNameOrName[i];
        }
        return serviceFullPathName;
    }

    public static BaseServiceInfo create(ApplicationConfiguration applicationConfiguration, String... servicePathNameOrName) throws BaseServiceInfoException {
        BaseServiceInfo baseServiceInfo = new BaseServiceInfo();
        String serviceAddressedPathName = getAddressedPathName(servicePathNameOrName);
        try {
            if (serviceAddressedPathName.contains(".")) {
                //received a full path to a service name
                baseServiceInfo.setPath(serviceAddressedPathName.substring(0, serviceAddressedPathName.lastIndexOf(ConfigurationProperties.PATH_DELIMITER)));
                baseServiceInfo.setName(serviceAddressedPathName.substring(serviceAddressedPathName.lastIndexOf(ConfigurationProperties.PATH_DELIMITER) + 1));
                baseServiceInfo.setProperties(applicationConfiguration.getProperties().getConfigurationSubtree(baseServiceInfo.getPath()));
                Objects.requireNonNull(baseServiceInfo.getProperties());
                Objects.requireNonNull(baseServiceInfo.getProperties().get(baseServiceInfo.getName()));

                if (baseServiceInfo.getProperties().get(baseServiceInfo.getName()) instanceof Map) {
                    //inline service
                    baseServiceInfo.setProperties((ConfigurationProperties) baseServiceInfo.getProperties().get(baseServiceInfo.getName()));
                }
                if (baseServiceInfo.getProperties().get(baseServiceInfo.getName()) instanceof String) {
                    //referenced service
                    baseServiceInfo.setPath(BaseService.SERVICE_BASE_PATH);
                    baseServiceInfo.setName((String) baseServiceInfo.getProperties().get(baseServiceInfo.getName()));
                    baseServiceInfo.setProperties(applicationConfiguration.getProperties().getConfigurationSubtree(baseServiceInfo.getPath() + ConfigurationProperties.PATH_DELIMITER + baseServiceInfo.getName()));
                }
            } else {
                //received a global service name
                baseServiceInfo.setPath(BaseService.SERVICE_BASE_PATH);
                baseServiceInfo.setName(serviceAddressedPathName);
                baseServiceInfo.setProperties(applicationConfiguration.getProperties().getConfigurationSubtree(baseServiceInfo.getPath() + ConfigurationProperties.PATH_DELIMITER + baseServiceInfo.getName()));
            }
        } catch (Exception e) {
            throw new BaseServiceInfoException("Unable to create BaseServiceInfo for service path[" + serviceAddressedPathName + "].");
        }
        return baseServiceInfo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConfigurationProperties getProperties() {
        return properties;
    }

    public void setProperties(ConfigurationProperties properties) {
        this.properties = properties;
    }

    public String getFullPathName() {
        return BaseServiceInfo.getAddressedPathName(this.getPath(), this.getName());
    }

    public boolean isGlobal() {
        return this.getPath().equalsIgnoreCase(BaseService.SERVICE_BASE_PATH);
    }

    @Override
    public String toString() {
        return "BaseServiceInfo{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                '}';
    }
}
