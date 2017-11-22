package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars;

import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class HandlebarsTemplateLoader implements TemplateLoader {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String prefix;
    private String suffix;
    private HandlebarsTemplateService handlebarsTemplateService;

    public HandlebarsTemplateLoader(String prefix, String suffix, HandlebarsTemplateService handlebarsTemplateService) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.handlebarsTemplateService = handlebarsTemplateService;
    }

    @Override
    public TemplateSource sourceAt(String location) throws IOException {
        return null;
    }

    @Override
    public String resolve(String name) {
        return prefix + name + suffix;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
