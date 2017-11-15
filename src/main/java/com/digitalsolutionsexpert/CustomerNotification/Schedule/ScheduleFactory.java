package com.digitalsolutionsexpert.CustomerNotification.Schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleFactory {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(BaseSchedule.class, new BaseScheduleDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    public BaseSchedule create(ConfigurationProperties properties) throws ScheduleException {
        BaseSchedule baseSchedule = null;
        try {
            String scheduleDefinition = (new ObjectMapper()).writeValueAsString(properties);
            baseSchedule = this.create(scheduleDefinition);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ScheduleException("Unable to create schedule for given configuration.", e);
        } catch (ScheduleException e) {
            throw e;
        }
        return baseSchedule;
    }

    public BaseSchedule[] createArray(ConfigurationProperties properties) throws ScheduleException {
        List<BaseSchedule> baseSceduleList = new ArrayList<BaseSchedule>();
        for(int i = 1; i <= properties.size(); i++){
            BaseSchedule baseSchedule = create(properties.getConfigurationSubtree("[" + i + "]"));
            baseSceduleList.add(baseSchedule);
        }
        return (BaseSchedule[]) baseSceduleList.toArray(new BaseSchedule[baseSceduleList.size()]);
    }

    public BaseSchedule create(String scheduleDefinition) throws ScheduleException {
        try {
            return objectMapper.readValue(scheduleDefinition, BaseSchedule.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ScheduleException("Unable to create schedule for given configuration.", e);
        }
    }

    public BaseSchedule[] createArray(String scheduleDefinition) throws ScheduleException {
        try {
            return objectMapper.readValue(scheduleDefinition, BaseSchedule[].class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ScheduleException("Unable to create schedule array for given configuration.", e);
        }
    }
}
