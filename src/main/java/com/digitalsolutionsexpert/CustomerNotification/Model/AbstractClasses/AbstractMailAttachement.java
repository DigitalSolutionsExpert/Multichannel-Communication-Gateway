package com.digitalsolutionsexpert.CustomerNotification.Model.AbstractClasses;

import javax.activation.DataSource;

public abstract class AbstractMailAttachement implements DataSource {
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
