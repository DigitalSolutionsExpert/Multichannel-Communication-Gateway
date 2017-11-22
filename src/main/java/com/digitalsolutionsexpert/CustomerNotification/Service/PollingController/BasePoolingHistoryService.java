package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.BaseSchedule;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceScheduleListener;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseThreadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;


public class BasePoolingHistoryService extends BaseThreadedService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private BasePollingControllerService pollingControllerService;
    private BaseServiceScheduleListener scheduleListenerHistoryService;
    private int size;

    public BasePoolingHistoryService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
        this.scheduleListenerHistoryService = new BaseServiceScheduleListener() {
            @Override
            public void onSchedule(BaseSchedule schedule) {
                BasePoolingHistoryService.this.history();
            }
        };
        this.addScheduleListener("schedule-polling-history", this.scheduleListenerHistoryService);
        this.size = Integer.valueOf(String.valueOf(this.getProperties().get("size", "1000")));

        this.requestStart();
    }

    public BasePollingControllerService getPollingControllerService() {
        return pollingControllerService;
    }

    public void setPollingControllerService(BasePollingControllerService pollingControllerService) {
        this.pollingControllerService = pollingControllerService;
    }

    private void history() {
        if (this.getPollingControllerService() == null) {
            return;
        }
        try {
            this.getPollingControllerService().getPersistenceService().moveToHistory(this.size);
        } catch (Exception e) {
            logger.error(e.getStackTrace()[0].getMethodName(), e);
        }
    }
}
