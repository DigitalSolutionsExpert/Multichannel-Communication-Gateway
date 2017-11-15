package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.ServiceAdapters;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.BaseServiceAdapterExecutionResult;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail.MailServiceAdapter;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail.MailServiceAdapterRequest;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceExecutionStatus;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.BasePollingConsumerService;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingRequest;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingResponse;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling.FormatPollingStatus;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.Persistence.BasePollingPersistenceServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.BaseTemplateServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail.MailTemplateService;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class FormatPoolingConsumerMailSrvAdpt extends BasePollingConsumerService<FormatPollingRequest, FormatPollingResponse, FormatPollingStatus> {
    private MailServiceAdapter mailServiceAdapter;
    private MailTemplateService mailTemplateService;

    public FormatPoolingConsumerMailSrvAdpt(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(applicationConfiguration, path, name);
        this.mailServiceAdapter = (MailServiceAdapter) this.getApplicationConfiguration().getService(path + "." + name + "." + "service" + "." + "service-mail-send");
        this.mailTemplateService = (MailTemplateService) this.getApplicationConfiguration().getService(path + "." + name + "." + "service" + "." + "service-mail-template");
    }

    @Override
    public void run() {
        FormatPollingStatus formatPollingStatus = null;
        try {
            FormatPollingRequest request = this.getRequest();
            formatPollingStatus = new FormatPollingStatus();
            System.out.println("Executing mail request on [" + Thread.currentThread().toString() + "] " + request.toString());

            MailServiceAdapterRequest mailServiceAdapterRequest = this.mailTemplateService.apply(request.getTemplateId(), request.getRequestData());

            FormatPollingStatus formatPollingStatusBS = new FormatPollingStatus();
            formatPollingStatusBS.setStatus(BaseServiceExecutionStatus.STATUS_IN_PROCESS);
            formatPollingStatusBS.setStatusReason("BGNSEND");
            formatPollingStatusBS.setDescription(null);
            formatPollingStatusBS.setDefinitive(true);
            formatPollingStatusBS.setStatusDate(new Date());
            formatPollingStatusBS.setSuccess(false);
            this.getPollingControllerService().getPersistenceService().updateStatus(this.getRequest(), null, formatPollingStatusBS);

            BaseServiceAdapterExecutionResult mailServiceAdapterExecutionResult = this.mailServiceAdapter.execute(mailServiceAdapterRequest);
            formatPollingStatus.setStatus(mailServiceAdapterExecutionResult.getStatus().getStatus());
            formatPollingStatus.setStatusReason(mailServiceAdapterExecutionResult.getStatus().getStatusReason());
            formatPollingStatus.setDescription(mailServiceAdapterExecutionResult.getStatus().getDescription());
            formatPollingStatus.setDefinitive(mailServiceAdapterExecutionResult.getStatus().isDefinitive());
            formatPollingStatus.setStatusDate(mailServiceAdapterExecutionResult.getStatus().getStatusDate());
            formatPollingStatus.setSuccess(mailServiceAdapterExecutionResult.getStatus().isSuccess());

        } catch (BaseTemplateServiceException e) {
            e.printStackTrace();
            formatPollingStatus.setStatus(BaseServiceExecutionStatus.STATUS_EXCPEPTION);
            formatPollingStatus.setStatusReason(e.getClass().getSimpleName());
            formatPollingStatus.setDescription(e.getMessage());
            formatPollingStatus.setDefinitive(e.isDefinitive());
            formatPollingStatus.setStatusDate(new Date());
            formatPollingStatus.setSuccess(false);
        } catch (BasePollingPersistenceServiceException e) {
            e.printStackTrace();
            formatPollingStatus.setStatus(BaseServiceExecutionStatus.STATUS_EXCPEPTION);
            formatPollingStatus.setStatusReason(e.getClass().getSimpleName());
            formatPollingStatus.setDescription(e.getMessage());
            formatPollingStatus.setDefinitive(e.isDefinitive());
            formatPollingStatus.setStatusDate(new Date());
            formatPollingStatus.setSuccess(false);
        } catch (Exception e) {
            e.printStackTrace();
            formatPollingStatus.setStatus(BaseServiceExecutionStatus.STATUS_EXCPEPTION);
            formatPollingStatus.setStatusReason(e.getClass().getSimpleName());
            formatPollingStatus.setDescription(e.getMessage());
            formatPollingStatus.setDefinitive(true);
            formatPollingStatus.setStatusDate(new Date());
            formatPollingStatus.setSuccess(false);
        } finally {
            try {
                this.getPollingControllerService().getPersistenceService().updateStatus(this.getRequest(), null, formatPollingStatus);
            } catch (BasePollingPersistenceServiceException e) {
                e.printStackTrace();
            }
        }
    }
}
