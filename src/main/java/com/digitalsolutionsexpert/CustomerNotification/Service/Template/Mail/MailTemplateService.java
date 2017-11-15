package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.DataValidator.DataValidationReport;
import com.digitalsolutionsexpert.CustomerNotification.DataValidator.IDataValidator;
import com.digitalsolutionsexpert.CustomerNotification.Datasource.BaseDatasource;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.BaseSchedule;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail.MailServiceAdapterRequest;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceScheduleListener;
import com.digitalsolutionsexpert.CustomerNotification.Service.Persistence.PersistenceServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.BaseTemplateService;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.BaseTemplateServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail.Persistence.BaseMailTemplateConfigurationPersistenceService;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail.Persistence.MailTemplate;
import org.apache.commons.lang3.StringUtils;


import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class MailTemplateService extends BaseTemplateService {
    private BaseMailTemplateConfigurationPersistenceService persistenceService;
    private Date loadDate;
    private Map<Number, MailTemplate> mailTemplateMap;
    private BaseTemplateService templateEngineService;
    private BaseServiceScheduleListener loadConfigurationListener;

    public MailTemplateService(ApplicationConfiguration applicationConfiguration, String path, String name) throws NoSuchMethodException, IOException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, BaseServiceException {
        super(applicationConfiguration, path, name);
        this.persistenceService = (BaseMailTemplateConfigurationPersistenceService) this.getApplicationConfiguration().getService(path + "." + name + "." + "service" + "." + "service-mail-template-persistence");
        this.templateEngineService = (BaseTemplateService) this.getApplicationConfiguration().getService(path + "." + name + "." + "service" + "." + "service-template-engine");
        this.loadDate = null;

        String scheduleNameLoadConfiguration = String.valueOf(this.getProperties().get("listener-load-configuration-schedule"));
        if (scheduleNameLoadConfiguration != null) {
            this.loadConfigurationListener = new BaseServiceScheduleListener() {
                @Override
                public void onSchedule(BaseSchedule schedule) {
                    try {
                        MailTemplateService.this.loadConfiguration(false);
                    } catch (PersistenceServiceException e) {
                        e.printStackTrace();
                    }
                }
            };
            this.addScheduleListener(scheduleNameLoadConfiguration, loadConfigurationListener);
        }

        this.loadConfiguration(true);

        this.requestStart();
    }

    @Override
    public MailServiceAdapterRequest apply(Number templateId, Object payload) throws BaseTemplateServiceException {
        MailServiceAdapterRequest mailSendRequest = new MailServiceAdapterRequest();
        try {
            MailTemplate mailTemplate = this.getMailTemplate(templateId);
            Objects.requireNonNull(mailTemplate, "Mail template with uuid " + templateId + " not found.");

            //validate request data
            IDataValidator dataValidator = mailTemplate.getDataValidator();
            Object payloadValidation = dataValidator.convertToValidationFormat(payload);
            DataValidationReport dataValidationReport = dataValidator.validate(payloadValidation);
            if (!dataValidationReport.isSuccess()) {
                throw new MailTemplateServiceException(StringUtils.join(dataValidationReport.getMessageList(), "\n"));
            }

            //process request data
            Object payloadProcess = dataValidator.convertToProcessingFormat(payload);

            ObjectMapper objectMapper = new ObjectMapper();

            //process fromFormatId
            String fromString = (String) templateEngineService.apply(mailTemplate.getFromFormatId(), payloadProcess);
            if (fromString != null) {
                mailSendRequest.setFrom(objectMapper.readValue(fromString, InternetAddress.class));
            }
            //process senderFormatId
            String senderString = (String) templateEngineService.apply(mailTemplate.getSenderFormatId(), payloadProcess);
            if (senderString != null) {
                mailSendRequest.setSender(objectMapper.readValue(senderString, InternetAddress.class));
            }
            //process toFormatId
            String toString = (String) templateEngineService.apply(mailTemplate.getToFormatId(), payloadProcess);
            if (toString != null) {
                mailSendRequest.setTo(objectMapper.readValue(toString, InternetAddress[].class));
            }
            //process ccFormatId
            String ccString = (String) templateEngineService.apply(mailTemplate.getCcFormatId(), payloadProcess);
            if (ccString != null) {
                mailSendRequest.setCc(objectMapper.readValue(ccString, InternetAddress[].class));
            }
            //process bccFormatId
            String bccString = (String) templateEngineService.apply(mailTemplate.getBccFormatId(), payloadProcess);
            if (bccString != null) {
                mailSendRequest.setBcc(objectMapper.readValue(bccString, InternetAddress[].class));
            }
            //process subjectFormatId
            String subjectString = (String) templateEngineService.apply(mailTemplate.getSubjectFormatId(), payloadProcess);
            if (subjectString != null) {
                mailSendRequest.setSubject(subjectString);
            }
            //process bodyFormatId
            String bodyString = (String) templateEngineService.apply(mailTemplate.getBodyFormatId(), payloadProcess);
            if (bodyString != null) {
                mailSendRequest.setBody(bodyString);
            }
            //process bodyFormat
            mailSendRequest.setBodyType(mailTemplate.getBodyFormat());
            //process attachementFormatId
            String attachementString = (String) templateEngineService.apply(mailTemplate.getAttachementFormatId(), payloadProcess);
            if (attachementString != null) {
                mailSendRequest.setAttachements(objectMapper.readValue(attachementString, BaseDatasource[].class));
            }
        } catch (BaseTemplateServiceException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseTemplateServiceException(e);
        }
        return mailSendRequest;
    }


    private void loadConfiguration(boolean throwException) throws PersistenceServiceException {
        try {
            System.out.println("Load configuration for service [" + this.getName() + "] instance [" + this.toString() + "] at " + new Date() + " on thread [" + Thread.currentThread().toString() + "]");

            //try load configurations
            Map<Number, MailTemplate> dbEMailTemplateMap = this.persistenceService.retrieveMailTemplates();

            //refresh configurations
            this.setMailTemplateMap(dbEMailTemplateMap);
            this.setLoadDate(new Date());

        } catch (PersistenceServiceException e) {
            e.printStackTrace();
            if (throwException) {
                throw new PersistenceServiceException(e);
            }
        }
    }


    public synchronized Date getLoadDate() {
        return loadDate;
    }

    public synchronized void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public synchronized Map<Number, MailTemplate> getMailTemplateMap() {
        return mailTemplateMap;
    }

    public synchronized void setMailTemplateMap(Map<Number, MailTemplate> mailTemplateMap) {
        this.mailTemplateMap = mailTemplateMap;
    }

    public MailTemplate getMailTemplate(Number key) {
        return this.getMailTemplateMap().get(Long.valueOf(String.valueOf(key)));
    }


}
