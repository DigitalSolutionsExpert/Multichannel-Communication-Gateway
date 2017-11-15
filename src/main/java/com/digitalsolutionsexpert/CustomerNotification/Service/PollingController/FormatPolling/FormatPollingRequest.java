package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.FormatPolling;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class FormatPollingRequest {
    private Number id;
    private Number templateId;
    private String requestData;

    private String[] businessKeyString;
    private Number[] businessKeyNumber;
    private Date[] businessKeyDate;

    private String statusCode;
    private String statusCodeRsn;
    private String statusDescription;
    private Date statusDate;

    private String serviceCode;

    private Date createDate;

    private Date updateDate;
    private String createBy;
    private String updateBy;

    public FormatPollingRequest() {
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Number templateId) {
        this.templateId = templateId;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String[] getBusinessKeyString() {
        return businessKeyString;
    }

    public void setBusinessKeyString(String[] businessKeyString) {
        this.businessKeyString = businessKeyString;
    }

    public Number[] getBusinessKeyNumber() {
        return businessKeyNumber;
    }

    public void setBusinessKeyNumber(Number[] businessKeyNumber) {
        this.businessKeyNumber = businessKeyNumber;
    }

    public Date[] getBusinessKeyDate() {
        return businessKeyDate;
    }

    public void setBusinessKeyDate(Date[] businessKeyDate) {
        this.businessKeyDate = businessKeyDate;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCodeRsn() {
        return statusCodeRsn;
    }

    public void setStatusCodeRsn(String statusCodeRsn) {
        this.statusCodeRsn = statusCodeRsn;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("templateId", templateId)
                .append("businessKeyString", businessKeyString)
                .append("businessKeyNumber", businessKeyNumber)
                .append("businessKeyDate", businessKeyDate)
                .append("createBy", createBy)
                .toString();
    }
}
