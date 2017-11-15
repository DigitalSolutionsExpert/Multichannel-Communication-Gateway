package com.digitalsolutionsexpert.CustomerNotification.Utils;

import com.google.common.base.CaseFormat;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class ConfigurationUtils {

    public static boolean containsKeyAnyCase(Object properties, Object key) {
        Objects.requireNonNull(properties, "Properties object must not be null.");
        if (properties instanceof Map) {
            return (((Map) properties).containsKey(key)) || (((Map) properties).containsKey(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key)));
        }

        if (properties instanceof Hashtable) {
            return (((Hashtable) properties).containsKey(key)) || (((Hashtable) properties).containsKey(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key)));
        }
        return false;
    }

    public static Object getAnyCase(Object properties, Object key) {
        if (properties != null && properties instanceof Map) {
            if (((Map) properties).containsKey(key)) {
                return ((Map) properties).get(key);
            }
            if (((Map) properties).containsKey(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key))) {
                return ((Map) properties).get(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key));
            }
        }
        if (properties != null && properties instanceof Hashtable) {
            if (((Hashtable) properties).containsKey(key)) {
                return ((Hashtable) properties).get(key);
            }
            if (((Hashtable) properties).containsKey(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key))) {
                return ((Hashtable) properties).get(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, (String) key));
            }
        }

        return null;
    }
}
