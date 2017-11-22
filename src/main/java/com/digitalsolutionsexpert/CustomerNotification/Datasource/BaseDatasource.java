package com.digitalsolutionsexpert.CustomerNotification.Datasource;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.UUID;

@JsonIgnoreProperties({"SIZE_UNKNOWN", "applicationConfiguration", "prepared", "uuid"})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class BaseDatasource implements DataSource {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static long SIZE_UNKNOWN = -1;

    private ApplicationConfiguration applicationConfiguration;
    protected String uuid;
    private String alias;
    private boolean prepared;

    public BaseDatasource() throws DatasourceException {
        this.uuid = UUID.randomUUID().toString();
        this.setPrepared(false);
        this.alias = null;
    }

    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    public void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @JsonGetter("@class")
    public String getClassName() {
        return this.getClass().getCanonicalName();
    }

    public String getUuid() {
        return uuid;
    }

    public String getAlias() {
        return (this.alias == null) ? this.getName() : this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public abstract long getSize();

    public boolean isSizeAware() {
        return this.getSize() > SIZE_UNKNOWN;
    }

    public abstract void prepare() throws DatasourceException;

    public boolean isPrepared() {
        return this.prepared;
    }


    protected void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }
}
