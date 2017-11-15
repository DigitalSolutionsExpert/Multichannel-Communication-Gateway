package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars;

import com.github.jknack.handlebars.io.AbstractTemplateSource;

import java.io.IOException;

import static org.apache.commons.lang3.Validate.notNull;

public class HandlebarsTemplateSource extends AbstractTemplateSource {

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
            e.printStackTrace();
        }
        return -1;
    }
}
