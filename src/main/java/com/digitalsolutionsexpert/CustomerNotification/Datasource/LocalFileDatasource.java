package com.digitalsolutionsexpert.CustomerNotification.Datasource;

import javax.activation.FileDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LocalFileDatasource extends BaseDatasource {
    private String pathname;
    private FileDataSource datasource;

    //Json serialization example
    /*
    {
        @class: "com.digitalsolutionsexpert.CustomerNotification.Datasource.LocalFileDatasource"
        pathname: "C:\\Users\\lucian.dragomir\\IdeaProjects\\vdfcustomernotificationemail\\src\\resource\\config.yaml"
    }
    */
    public LocalFileDatasource() {
        super();
    }

    public LocalFileDatasource(String pathname) {
        super();
        this.pathname = pathname;
    }

    @Override
    public long getSize() {
        if (!this.isPrepared()) {
            return BaseDatasource.SIZE_UNKNOWN;
        } else {
            return this.datasource.getFile().length();
        }
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.uuid = pathname;
        this.pathname = pathname;
        this.datasource = new FileDataSource(pathname);
    }


    @Override
    public void prepare() throws DatasourceException {
        if (!this.datasource.getFile().exists()) {
            throw new DatasourceException("Local file with name [" + this.pathname + "] does not exist.");
        }
        if (!this.datasource.getFile().isFile()) {
            throw new DatasourceException("The object with name [" + this.pathname + "] is not a file.");
        }
        if (!this.datasource.getFile().canRead()) {
            throw new DatasourceException("The file with name [" + this.pathname + "] can not be read.");
        }
        this.setPrepared(true);
    }

    public InputStream getInputStream() throws IOException {
        return this.datasource.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.datasource.getOutputStream();
    }

    public String getContentType() {
        return this.datasource.getContentType();
    }

    public String getName() {
        return this.datasource.getName();
    }
}
