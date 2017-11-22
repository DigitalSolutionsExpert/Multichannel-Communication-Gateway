package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.BaseSchedule;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceScheduleListener;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseThreadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class BasePoolingProducerService<REQUEST, RESPONSE, STATUS> extends BaseThreadedService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private BasePollingControllerService pollingControllerService;
    private BaseServiceScheduleListener scheduleListenerProducerService;
    private Class<? extends BasePollingConsumerService<REQUEST, RESPONSE, STATUS>> pollingConsumerServiceClass;
    private ConfigurationProperties pollingConsumerServiceProperties;

    public BasePoolingProducerService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
        this.scheduleListenerProducerService = new BaseServiceScheduleListener() {
            @Override
            public void onSchedule(BaseSchedule schedule) {
                BasePoolingProducerService.this.poll();
            }
        };
        this.addScheduleListener("schedule-polling-producer", this.scheduleListenerProducerService);
        this.requestStart();
    }

    public BasePollingControllerService getPollingControllerService() {
        return pollingControllerService;
    }

    public void setPollingControllerService(BasePollingControllerService pollingControllerService) {
        this.pollingControllerService = pollingControllerService;
    }

    private void poll() {
        try {
            if (this.getPollingControllerService() == null) {
                return;
            }
            logger.info("Polling producer [" + Thread.currentThread().toString() + "] at " + new Date());
            List poll = this.getPollingControllerService().getPersistenceService().poll(this.getPollingControllerService().getBlockingQueueFreeSize(), this.getPollingControllerService().getName());
            logger.info("Producer [" + Thread.currentThread().toString() + "] created [" + poll.size() + "] entries at " + new Date());

            Iterator iterator = poll.iterator();
            while (iterator.hasNext()) {
                BasePollingConsumerService<REQUEST, RESPONSE, STATUS> pollingConsumerService = (BasePollingConsumerService<REQUEST, RESPONSE, STATUS>) this.getApplicationConfiguration().getService(getPollingControllerService().getPath() + "." + getPollingControllerService().getName() + "." + "service" + "." + "service-polling-consumer", ApplicationConfiguration.BaseServiceGetMode.CREATE_ALWAYS);
                pollingConsumerService.setPollingControllerService(this.getPollingControllerService());
                pollingConsumerService.setRequest((REQUEST) iterator.next());
                getPollingControllerService().getThreadPoolExecutor().execute(pollingConsumerService);
            }
        } catch (Throwable e) {
            try {
                logger.error(e.getStackTrace()[0].getMethodName(), e);
            } catch (Throwable ex) {
            }
        }
    }
}
