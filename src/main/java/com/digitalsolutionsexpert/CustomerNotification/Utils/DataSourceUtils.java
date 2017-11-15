package com.digitalsolutionsexpert.CustomerNotification.Utils;

import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public final class DataSourceUtils {

    public  static DataSource createDataSource(ConfigurationProperties properties){
        BasicDataSource ds = new BasicDataSource();

        if(properties.containsKey("connection-properties")) {
            ds.setConnectionProperties((String)properties.get("connection-properties"));
        }
        if(properties.containsKey("default-auto-commit")) {
            ds.setDefaultAutoCommit((Boolean) properties.get("default-auto-commit"));
        }
        if(properties.containsKey("default-catalog")) {
            ds.setDefaultCatalog((String)properties.get("default-catalog"));
        }
        if(properties.containsKey("default-query-timeout")) {
            ds.setDefaultQueryTimeout((Integer) properties.get("default-query-timeout"));
        }
        if(properties.containsKey("default-transaction-isolation")) {
            ds.setDefaultTransactionIsolation((Integer)properties.get("default-transaction-isolation"));
        }
        if(properties.containsKey("driver-class-name")) {
            ds.setDriverClassName((String)properties.get("driver-class-name"));
        }
        if(properties.containsKey("enable-auto-commit-on-return")) {
            ds.setEnableAutoCommitOnReturn((Boolean)properties.get("enable-auto-commit-on-return"));
        }
        if(properties.containsKey("initial-size")) {
            ds.setInitialSize((Integer)properties.get("initial-size"));
        }
        if(properties.containsKey("max-idle")) {
            ds.setMaxIdle((Integer)properties.get("max-idle"));
        }
        if(properties.containsKey("max-open-prepared-statements")) {
            ds.setMaxOpenPreparedStatements((Integer)properties.get("max-open-prepared-statements"));
        }
        if(properties.containsKey("max-total")) {
            ds.setMaxTotal((Integer)properties.get("max-total"));
        }
        if(properties.containsKey("min-idle")) {
            ds.setMinIdle((Integer)properties.get("min-idle"));
        }
        if(properties.containsKey("password")) {
            ds.setPassword((String)properties.get("password"));
        }
        if(properties.containsKey("rollback-on-return")) {
            ds.setRollbackOnReturn((Boolean)properties.get("rollback-on-return"));
        }
        if(properties.containsKey("test-on-borrow")) {
            ds.setTestOnBorrow((Boolean)properties.get("test-on-borrow"));
        }
        if(properties.containsKey("test-on-create")) {
            ds.setTestOnCreate((Boolean)properties.get("test-on-create"));
        }
        if(properties.containsKey("test-on-return")) {
            ds.setTestOnReturn((Boolean)properties.get("test-on-return"));
        }
        if(properties.containsKey("url")) {
            ds.setUrl((String)properties.get("url"));
        }
        if(properties.containsKey("username")) {
            ds.setUsername((String)properties.get("username"));
        }
        if(properties.containsKey("validation-query")) {
            ds.setValidationQuery((String)properties.get("validation-query"));
        }
        if(properties.containsKey("validation-query-timeout")) {
            ds.setValidationQueryTimeout((Integer)properties.get("validation-query-timeout"));
        }

        return ds;
    }
}
