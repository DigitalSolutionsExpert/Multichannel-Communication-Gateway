package com.digitalsolutionsexpert.CustomerNotification.Datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class ServiceAdapterFileDatasource extends BaseDatasource {
    private String serviceName;
    private HashMap<String, String> serviceParameters;

    public ServiceAdapterFileDatasource() {
        super();
        this.serviceName = null;
        this.serviceParameters = new HashMap<>();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public HashMap<String, String> getServiceParameters() {
        return serviceParameters;
    }

    public void setServiceParameters(HashMap<String, String> serviceParameters) {
        this.serviceParameters = serviceParameters;
    }

    @Override
    public long getSize() {
        return BaseDatasource.SIZE_UNKNOWN;
    }

    @Override
    public void prepare() throws DatasourceException {

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
