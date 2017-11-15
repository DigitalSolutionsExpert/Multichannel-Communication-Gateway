package com.digitalsolutionsexpert.CustomerNotification.Schedule;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class BaseScheduleDeserializer extends JsonDeserializer<BaseSchedule> {
    public static final String[] CLASS_NAME_PROPERTIES = new String[]{"class-name", "className"};

    @Override
    public BaseSchedule deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper objectMapper = (ObjectMapper)jsonParser.getCodec();
        JsonNode jsonNode = objectMapper.readTree(jsonParser);
        String className = null;
        Class clazz = null;
        boolean classNameFound = false;
        int i = 0;
        while (!classNameFound && i < CLASS_NAME_PROPERTIES.length) {
            if (jsonNode.has(CLASS_NAME_PROPERTIES[i])) {
                className = jsonNode.get(CLASS_NAME_PROPERTIES[i]).textValue();
                classNameFound = true;
            }
            i++;
        }
        if (classNameFound) {
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new JsonMappingException("Unable to deserialize BaseSchedule.", e);
            }
            return (BaseSchedule) objectMapper.treeToValue(jsonNode, clazz);
        } else {
            throw new JsonMappingException("Unable to deserialize BaseSchedule, missing class-name property.");
        }
    }
}
