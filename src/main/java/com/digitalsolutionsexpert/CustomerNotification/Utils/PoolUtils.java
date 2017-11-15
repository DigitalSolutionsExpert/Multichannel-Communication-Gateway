package com.digitalsolutionsexpert.CustomerNotification.Utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class PoolUtils {
    public static GenericObjectPoolConfig getGenericObjectPoolConfig(Object properties) {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        if (ConfigurationUtils.containsKeyAnyCase(properties, "maxIdle")) {
            genericObjectPoolConfig.setMaxIdle(Integer.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "maxIdle"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "maxTotal")) {
            genericObjectPoolConfig.setMaxTotal(Integer.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "maxTotal"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "minIdle")) {
            genericObjectPoolConfig.setMinIdle(Integer.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "minIdle"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "lifo")) {
            genericObjectPoolConfig.setLifo(Boolean.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "lifo"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "fairness")) {
            genericObjectPoolConfig.setLifo(Boolean.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "fairness"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "maxWaitMillis")) {
            genericObjectPoolConfig.setMaxWaitMillis(Long.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "maxWaitMillis"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "minEvictableIdleTimeMillis")) {
            genericObjectPoolConfig.setMinEvictableIdleTimeMillis(Long.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "minEvictableIdleTimeMillis"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "softMinEvictableIdleTimeMillis")) {
            genericObjectPoolConfig.setSoftMinEvictableIdleTimeMillis(Long.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "softMinEvictableIdleTimeMillis"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "numTestsPerEvictionRun")) {
            genericObjectPoolConfig.setNumTestsPerEvictionRun(Integer.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "numTestsPerEvictionRun"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "testOnCreate")) {
            genericObjectPoolConfig.setTestOnCreate(Boolean.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "testOnCreate"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "testOnBorrow")) {
            genericObjectPoolConfig.setTestOnBorrow(Boolean.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "testOnBorrow"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "testOnReturn")) {
            genericObjectPoolConfig.setTestOnReturn(Boolean.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "testOnReturn"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "testWhileIdle")) {
            genericObjectPoolConfig.setTestWhileIdle(Boolean.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "testWhileIdle"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "timeBetweenEvictionRunsMillis")) {
            genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(Integer.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "timeBetweenEvictionRunsMillis"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "evictionPolicyClassName")) {
            genericObjectPoolConfig.setEvictionPolicyClassName(String.valueOf(ConfigurationUtils.getAnyCase(properties, "evictionPolicyClassName")));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "blockWhenExhausted")) {
            genericObjectPoolConfig.setBlockWhenExhausted(Boolean.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "blockWhenExhausted"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "jmxEnabled")) {
            genericObjectPoolConfig.setJmxEnabled(Boolean.valueOf(String.valueOf(ConfigurationUtils.getAnyCase(properties, "jmxEnabled"))));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "jmxNameBase")) {
            genericObjectPoolConfig.setJmxNameBase(String.valueOf(ConfigurationUtils.getAnyCase(properties, "jmxNameBase")));
        }
        if (ConfigurationUtils.containsKeyAnyCase(properties, "jmxNamePrefix")) {
            genericObjectPoolConfig.setJmxNamePrefix(String.valueOf(ConfigurationUtils.getAnyCase(properties, "jmxNamePrefix")));
        }

        return genericObjectPoolConfig;
    }

}
