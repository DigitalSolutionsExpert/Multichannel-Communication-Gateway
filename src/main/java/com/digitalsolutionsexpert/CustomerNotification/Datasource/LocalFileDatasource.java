package com.digitalsolutionsexpert.CustomerNotification.Datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.FileDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;

public class LocalFileDatasource extends BaseDatasource {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String pathname;
    private FileDataSource datasource;

    //Json serialization example
    /*
    {
        @class: "com.digitalsolutionsexpert.CustomerNotification.Datasource.LocalFileDatasource"
        pathname: "C:\\Users\\lucian.dragomir\\IdeaProjects\\vdfcustomernotificationemail\\src\\resource\\config.yaml"
    }
    */
    public LocalFileDatasource() throws DatasourceException {
        super();
    }

    public LocalFileDatasource(String pathname) throws DatasourceException {
        super();
        this.setPathname(pathname);
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

    public void setPathname(String pathname) throws DatasourceException {
        this.uuid = pathname;
        this.pathname = pathname;
        this.datasource = new FileDataSource(pathname);
        this.prepare();
    }


    @Override
    public synchronized void prepare() throws DatasourceException {
        try {
            if (!this.isPrepared()) {
                if (!this.getDatasource().getFile().exists()) {
                    throw new DatasourceException("Local file with name [" + this.pathname + "] does not exist.");
                }
                if (!this.getDatasource().getFile().isFile()) {
                    throw new DatasourceException("The object with name [" + this.pathname + "] is not a file.");
                }
                if (!this.getDatasource().getFile().canRead()) {
                    throw new DatasourceException("The file with name [" + this.pathname + "] can not be read.");
                }
                this.setPrepared(true);
            }
        } catch (IOException e) {
            throw new DatasourceException(e);
        }
    }

    public InputStream getInputStream() throws IOException {
        return this.getDatasource().getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.getDatasource().getOutputStream();
    }

    public String getContentType() {
        return this.datasource.getContentType();
    }

    public String getName() {
        return this.datasource.getName();
    }

    private FileDataSource getDatasource() throws IOException {
        if (this.datasource == null) {
            throw new IOException("A file was not specified for the LocalFileDatasource instance.");
        }
        return this.datasource;
    }
}
