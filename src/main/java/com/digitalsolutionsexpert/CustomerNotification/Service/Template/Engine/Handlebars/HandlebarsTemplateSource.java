package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars;

import com.github.jknack.handlebars.io.AbstractTemplateSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import static org.apache.commons.lang3.Validate.notNull;

public class HandlebarsTemplateSource extends AbstractTemplateSource {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String filename;
    private HandlebarsTemplateService handlebarsTemplateService;

    public HandlebarsTemplateSource(String filename, HandlebarsTemplateService handlebarsTemplateService) {
        this.filename = notNull(filename, "The name is required for HandlebarsTemplateSource.");
        ;
        this.handlebarsTemplateService = handlebarsTemplateService;
    }

    @Override
    public String content() throws IOException {
        return (this.handlebarsTemplateService.getHandlebarsTemplate(Long.valueOf(String.valueOf(filename))).getContent());
    }

    @Override
    public String filename() {
        return this.filename;
    }

    @Override
    public long lastModified() {
        try {
            return this.content().hashCode();
        } catch (IOException e) {
            logger.error(e.getStackTrace()[0].getMethodName(), e);
        }
        return -1;
    }
}
