package com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail.Persistence;

import com.digitalsolutionsexpert.CustomerNotification.DataValidator.IDataValidator;

import java.util.Date;

public class MailTemplate {
    private Number id;
    private String code;
    private String name;
    private String description;
    private String status;
    private Number fromFormatId;
    private Number senderFormatId;
    private Number toFormatId;
    private Number ccFormatId;
    private Number bccFormatId;
    private Number subjectFormatId;
    private Number bodyFormatId;
    private String bodyFormat;
    private Number attachementFormatId;
    private IDataValidator dataValidator;

    private Date createDate;
    private Date updateDate;
    private String createBy;
    private String updateBy;

    public MailTemplate() {
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Number getFromFormatId() {
        return fromFormatId;
    }

    public void setFromFormatId(Number fromFormatId) {
        this.fromFormatId = fromFormatId;
    }

    public Number getSenderFormatId() {
        return senderFormatId;
    }

    public void setSenderFormatId(Number senderFormatId) {
        this.senderFormatId = senderFormatId;
    }

    public Number getToFormatId() {
        return toFormatId;
    }

    public void setToFormatId(Number toFormatId) {
        this.toFormatId = toFormatId;
    }

    public Number getCcFormatId() {
        return ccFormatId;
    }

    public void setCcFormatId(Number ccFormatId) {
        this.ccFormatId = ccFormatId;
    }

    public Number getBccFormatId() {
        return bccFormatId;
    }

    public void setBccFormatId(Number bccFormatId) {
        this.bccFormatId = bccFormatId;
    }

    public Number getSubjectFormatId() {
        return subjectFormatId;
    }

    public void setSubjectFormatId(Number subjectFormatId) {
        this.subjectFormatId = subjectFormatId;
    }

    public Number getBodyFormatId() {
        return bodyFormatId;
    }

    public void setBodyFormatId(Number bodyFormatId) {
        this.bodyFormatId = bodyFormatId;
    }

    public String getBodyFormat() {
        return bodyFormat;
    }

    public void setBodyFormat(String bodyFormat) {
        this.bodyFormat = bodyFormat;
    }

    public Number getAttachementFormatId() {
        return attachementFormatId;
    }

    public void setAttachementFormatId(Number attachementFormatId) {
        this.attachementFormatId = attachementFormatId;
    }

    public IDataValidator getDataValidator() {
        return dataValidator;
    }

    public void setDataValidator(IDataValidator dataValidator) {
        this.dataValidator = dataValidator;
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
}
