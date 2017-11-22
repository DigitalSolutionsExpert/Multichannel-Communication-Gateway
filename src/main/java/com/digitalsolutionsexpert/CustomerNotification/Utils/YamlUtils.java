package com.digitalsolutionsexpert.CustomerNotification.Utils;


import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.regex.Pattern;

public final class YamlUtils {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static <T> T loadConfigFile(String configFileName) {
        Yaml yaml = new Yaml();
        T loadYaml = null;
        File configFile = new File(configFileName);
        if (configFile.exists() && !configFile.isDirectory()) {
            InputStream inputStreamYaml = null;
            try {
                inputStreamYaml = new FileInputStream(configFile);
                loadYaml = yaml.load(inputStreamYaml);
            } catch (FileNotFoundException e) {
                logger.error(e.getStackTrace()[0].getMethodName(), e);
            }
        }
        return loadYaml;
    }

    public static ConfigurationProperties getConfiguration(String configFileName, String prefix) {
        Map yaml = loadConfigFile(configFileName);
        ConfigurationProperties properties = null;
        Map remaining = yaml;
        if (prefix != null && remaining != null) {
            String[] parts = prefix.split(Pattern.quote("."));
            for (int i = 0; i < parts.length; i++) {
                String key = parts[i];
                if (remaining != null && remaining instanceof Map && remaining.containsKey(key)) {
                    remaining = (Map) remaining.get(key);
                } else {
                    remaining = null;
                }
            }
        }
        if (remaining != null && (remaining instanceof Map)) {
            properties = ConfigurationProperties.create(remaining);
        }
        return properties;
    }

    public static ConfigurationProperties getConfiguration(String configFileName) {
        return getConfiguration(configFileName, null);
    }

}
