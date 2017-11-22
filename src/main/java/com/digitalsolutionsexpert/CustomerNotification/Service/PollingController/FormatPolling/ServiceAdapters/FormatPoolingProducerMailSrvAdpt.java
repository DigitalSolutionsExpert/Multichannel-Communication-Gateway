package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.ServiceAdapters;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.BasePoolingProducerService;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingRequest;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingResponse;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class FormatPoolingProducerMailSrvAdpt extends BasePoolingProducerService<FormatPollingRequest, FormatPollingResponse, FormatPollingStatus> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public FormatPoolingProducerMailSrvAdpt(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

}
