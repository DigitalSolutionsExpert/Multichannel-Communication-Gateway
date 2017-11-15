package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.JsonNodeValueResolver;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.GuavaTemplateCache;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.BaseSchedule;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceScheduleListener;
import com.digitalsolutionsexpert.CustomerNotification.Service.Persistence.PersistenceServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.BaseTemplateServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.BaseTemplateService;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars.Persistence.HandlebarsConfigurationBasePersistenceService;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars.Persistence.HandlebarsTemplate;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HandlebarsTemplateService extends BaseTemplateService {
    private HandlebarsConfigurationBasePersistenceService persistenceService;
    private Date loadDate;
    private Map<Number, HandlebarsTemplate> handlebarsTemplateMap;
    private Handlebars handlebars;
    private BaseServiceScheduleListener loadConfigurationListener;

    public HandlebarsTemplateService(ApplicationConfiguration applicationConfiguration, String path, String name) throws NoSuchMethodException, BaseServiceException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        super(applicationConfiguration, path, name);
        this.persistenceService = (HandlebarsConfigurationBasePersistenceService) this.getApplicationConfiguration().getService(path + "." + name + "." + "service" + "." + "service-handlebars-template-persistence");
        this.loadDate = null;

        String scheduleNameLoadConfiguration = String.valueOf(this.getProperties().get("listener-load-configuration-schedule"));
        if (scheduleNameLoadConfiguration != null) {
            this.loadConfigurationListener = new BaseServiceScheduleListener() {
                @Override
                public void onSchedule(BaseSchedule schedule) {
                    try {
                        HandlebarsTemplateService.this.loadConfiguration(false);
                    } catch (PersistenceServiceException e) {
                        e.printStackTrace();
                    }
                }
            };
            this.addScheduleListener(scheduleNameLoadConfiguration, loadConfigurationListener);
        }

        this.loadConfiguration(true);

        TemplateLoader loader = new HandlebarsTemplateLoader("", "", this);
        final Cache<TemplateSource, Template> templateCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(1000).build();
        this.handlebars = new Handlebars(loader).with((new GuavaTemplateCache(templateCache)));

        //load handlebars helpers
        //class
        ConfigurationProperties cp = this.getProperties().getConfigurationSubtree("helper.class");
        if (cp != null) {
            for (int i = 1; i <= cp.size(); i++) {
                String className = String.valueOf(cp.get("[" + i + "]"));
                Class clazz = Class.forName(className);
                handlebars.registerHelpers(clazz);
            }
        }
        //instance to be implemented


        this.requestStart();
    }

    @Override
    public Object apply(Number templateId, Object payload) throws BaseTemplateServiceException {
        String result = null;
        if (templateId == null) {
            return null;
        }
        try {
            HandlebarsTemplate handlebarsTemplate = this.getHandlebarsTemplate(templateId);
            Objects.requireNonNull(handlebarsTemplate, "Format template with uuid " + templateId + " not found.");

            if (handlebarsTemplate.getType().equalsIgnoreCase("CONSTANT")) {
                result = handlebarsTemplate.getContent();
            } else {
                Template template = null;
                template = handlebars.compileInline(handlebarsTemplate.getContent());
                Context context = Context
                        .newBuilder(payload)
                        .resolver(JsonNodeValueResolver.INSTANCE,
                                JavaBeanValueResolver.INSTANCE,
                                FieldValueResolver.INSTANCE,
                                MapValueResolver.INSTANCE,
                                MethodValueResolver.INSTANCE).build();
                result = template.apply(context);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new HandlebarsTemplateServiceException(e);
        }
        return result;
    }

    private void loadConfiguration(boolean throwException) throws PersistenceServiceException {
        try {
            System.out.println("Load configuration for service [" + this.getName() + "] instance [" + this.toString() + "] at " + new Date() + " on thread [" + Thread.currentThread().toString() + "]");
            //try load configurations
            Map<Number, HandlebarsTemplate> dbFormatTemplateMap = this.persistenceService.retrieveFormatTemplates();

            //refresh configurations
            this.setHandlebarsTemplateMap(dbFormatTemplateMap);
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

    public synchronized Map<Number, HandlebarsTemplate> getHandlebarsTemplateMap() {
        return handlebarsTemplateMap;
    }

    public synchronized void setHandlebarsTemplateMap(Map<Number, HandlebarsTemplate> handlebarsTemplateMap) {
        this.handlebarsTemplateMap = handlebarsTemplateMap;
    }

    public HandlebarsTemplate getHandlebarsTemplate(Number key) {
        return this.handlebarsTemplateMap.get(Long.valueOf(String.valueOf(key)));
    }
}
