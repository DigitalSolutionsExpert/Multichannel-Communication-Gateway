package com.digitalsolutionsexpert.CustomerNotification.Model.AbstractClasses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import java.lang.invoke.MethodHandles;

public abstract class AbstractMailAttachement implements DataSource {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String fileName;

    public AbstractMailAttachement() {
    }

    public String getFileName() {
        return this.fileName == null ? this.getName() : this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
