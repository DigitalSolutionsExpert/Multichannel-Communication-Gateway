package com.digitalsolutionsexpert.CustomerNotification.Application;

import com.digitalsolutionsexpert.CustomerNotification.Service.*;
import com.digitalsolutionsexpert.CustomerNotification.Utils.DataSourceUtils;
import com.digitalsolutionsexpert.CustomerNotification.Utils.YamlUtils;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationConfiguration {
    private String configFileName;
    private ConfigurationProperties properties;
    private Map<String, DataSource> dataSourceMap;
    private Map<String, BaseService> serviceMap;

    public static enum BaseServiceGetMode {CREATE_IF_NOT_EXISTS, CREATE_ALWAYS}

    public ApplicationConfiguration(String configFileName) throws FileNotFoundException {
        this.configFileName = configFileName;
        this.properties = YamlUtils.getConfiguration(configFileName);
        this.dataSourceMap = new ConcurrentHashMap<String, DataSource>();
        this.serviceMap = new ConcurrentHashMap<String, BaseService>();
    }

    public ConfigurationProperties getProperties() {
        return this.properties;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public DataSource getDataSource(String key) {
        DataSource ds;
        if (this.dataSourceMap.containsKey(key)) {
            ds = this.dataSourceMap.get(key);
        } else {
            synchronized (new Object()) {
                if (this.dataSourceMap.containsKey(key)) {
                    ds = this.dataSourceMap.get(key);
                } else {
                    ds = DataSourceUtils.createDataSource(this.properties.getConfigurationSubtree("datasource." + key));
                    this.dataSourceMap.put(key, ds);
                    ds = this.dataSourceMap.get(key);
                }
            }
        }
        Objects.requireNonNull(ds);
        return ds;
    }

    public void registerService(BaseService service) {
        this.serviceMap.put(service.getPathName(), service);
    }

    public BaseService getService(String key) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ServiceFactoryException, BaseServiceInfoException {
        return this.getService(key, BaseServiceGetMode.CREATE_IF_NOT_EXISTS);
    }

    public BaseService getService(String key, BaseServiceGetMode getMode) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ServiceFactoryException, BaseServiceInfoException {
        BaseService service;
        BaseServiceInfo baseServiceInfo = BaseServiceInfo.create(this, key);
        String searchKey = baseServiceInfo.getFullPathName();
        if (BaseServiceGetMode.CREATE_IF_NOT_EXISTS.equals(getMode)) {
            if (this.serviceMap.containsKey(searchKey)) {
                service = this.serviceMap.get(searchKey);
            } else {
                synchronized (new Object()) {
                    if (this.serviceMap.containsKey(searchKey)) {
                        service = this.serviceMap.get(searchKey);
                    } else {
                        service = ServiceFactory.defaultServiceFactory.create(this, searchKey);
                        //the service constructor performs registration into map
                    }
                }
            }
        } else {
            service = ServiceFactory.defaultServiceFactory.create(this, searchKey);
        }
        Objects.requireNonNull(service);
        return service;
    }
}
