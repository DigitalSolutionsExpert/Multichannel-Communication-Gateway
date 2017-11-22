package com.digitalsolutionsexpert.CustomerNotification.Application;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ConfigurationProperties<K, V> extends ConcurrentHashMap<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static String PATH_DELIMITER = ".";
    public static String PATH_DELIMITER_PATTERN = Pattern.quote(PATH_DELIMITER);

    public V get(Object key, V defaultValue) {
        if ((!this.containsKey(key)) || (this.get(key) == null)) {
            return defaultValue;
        }
        return this.get(key);
    }

    @Override
    public V get(Object key) {
        if (super.get(key) != null) {
            return super.get(key);
        }
        if (super.get(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key)) != null) {
            return super.get(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key));
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
        //return super.containsKey(key) || super.containsKey(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key));
    }

    public ConfigurationProperties getConfigurationSubtree(String prefix) {
        ConfigurationProperties subtree = null;
        String[] parts = prefix.split(PATH_DELIMITER_PATTERN);
        Map remaining = this;
        for (int i = 0; i < parts.length; i++) {
            String key = parts[i];
            if (remaining != null && remaining instanceof Map && remaining.containsKey(key)) {
                remaining = (Map) remaining.get(key);
            } else {
                remaining = null;
            }
        }
        if (remaining != null && (remaining instanceof ConfigurationProperties)) {
            subtree = (ConfigurationProperties) remaining;
        }
        return subtree;
    }

    public static ConfigurationProperties create(Map map) {
        ConfigurationProperties configurationProperties = ConfigurationProperties.deepCopy(map);
        return configurationProperties;
    }

    public static ConfigurationProperties create(Hashtable hashtable) {
        ConfigurationProperties configurationProperties = ConfigurationProperties.deepCopy(hashtable);
        return configurationProperties;
    }

    private static ConfigurationProperties deepCopy(Map map) {
        ConfigurationProperties configurationProperties = new ConfigurationProperties();
        Iterator i$ = map.entrySet().iterator();
        while (i$.hasNext()) {
            Map.Entry entry = (Map.Entry) i$.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            Object valueCopy = value;
            if (value instanceof Map) {
                valueCopy = deepCopy((Map) value);
            }
            if (value instanceof Hashtable) {
                valueCopy = deepCopy((Hashtable) value);
            }
            if (value instanceof List) {
                valueCopy = deepCopy((List) value);
            }
            if (valueCopy != null) {
                configurationProperties.put(key, valueCopy);
            }
        }
        return configurationProperties;
    }

    private static ConfigurationProperties deepCopy(Hashtable hashtable) {
        ConfigurationProperties configurationProperties = new ConfigurationProperties();
        Iterator i$ = hashtable.entrySet().iterator();
        while (i$.hasNext()) {
            Map.Entry entry = (Map.Entry) i$.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            Object valueCopy = value;
            if (value instanceof Map) {
                valueCopy = deepCopy((Map) value);
            }
            if (value instanceof Hashtable) {
                valueCopy = deepCopy((Hashtable) value);
            }
            if (value instanceof List) {
                valueCopy = deepCopy((List) value);
            }

            configurationProperties.put(key, valueCopy);
        }
        return configurationProperties;
    }

    private static ConfigurationProperties deepCopy(List list) {
        ConfigurationProperties configurationProperties = new ConfigurationProperties();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object key = "[" + (i + 1) + "]";
                Object value = list.get(i);
                Object valueCopy = value;
                if (value instanceof Map) {
                    valueCopy = deepCopy((Map) value);
                }
                if (value instanceof Hashtable) {
                    valueCopy = deepCopy((Hashtable) value);
                }
                if (value instanceof List) {
                    valueCopy = deepCopy((List) value);
                }
                configurationProperties.put(key, valueCopy);
            }
        }
        return configurationProperties;
    }

    public Properties toProperties(String base, String search, String replace) {
        Properties properties = new Properties();
        Iterator i$ = this.entrySet().iterator();
        String searchQuoted = null;
        if (search != null) {
            searchQuoted = Pattern.quote(search);
        }
        while (i$.hasNext()) {
            Map.Entry entry = (Map.Entry) i$.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                String newKey;
                if (search == null) {
                    newKey = (base == null ? "" : base) + (String) key;
                } else {

                    newKey = (base == null ? "" : base) + ((String) key).replaceAll(searchQuoted, replace);
                }
                properties.put(newKey, String.valueOf(value));
            }
        }
        return properties;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}