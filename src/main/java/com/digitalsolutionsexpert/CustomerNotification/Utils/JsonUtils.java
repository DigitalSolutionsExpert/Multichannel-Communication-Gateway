package com.digitalsolutionsexpert.CustomerNotification.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static ObjectMapper defaultObjectMapper = createObjectMapper();

    public static ObjectMapper createObjectMapper(){
       ObjectMapper objectMapper = new ObjectMapper();
       return objectMapper;
    }

    public static ObjectMapper getDefaultObjectMapper(){
        return defaultObjectMapper;
    }
}
