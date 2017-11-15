package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.ServiceAdapters;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.BasePoolingProducerService;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingRequest;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingResponse;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingStatus;

public class FormatPoolingProducerMailSrvAdpt extends BasePoolingProducerService<FormatPollingRequest, FormatPollingResponse, FormatPollingStatus> {

    public FormatPoolingProducerMailSrvAdpt(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
    }

}
