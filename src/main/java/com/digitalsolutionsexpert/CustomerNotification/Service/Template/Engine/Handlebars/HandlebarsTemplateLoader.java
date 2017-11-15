package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Engine.Handlebars;

import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;

import java.io.IOException;

public class HandlebarsTemplateLoader implements TemplateLoader {
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
