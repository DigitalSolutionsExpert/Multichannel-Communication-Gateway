package com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail;

import com.digitalsolutionsexpert.CustomerNotification.Datasource.BaseDatasource;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.BaseServiceAdapterRequest;

import javax.mail.Address;

public class MailServiceAdapterRequest extends BaseServiceAdapterRequest {
    private Address from;
    private Address[] to;
    private Address[] cc;
    private Address[] bcc;
    private Address[] replyTo;
    private Address sender;
    private String subject;
    private String body;
    private String bodyType;
    private BaseDatasource[] attachements;

    public MailServiceAdapterRequest() {
        super();
    }

    public MailServiceAdapterRequest(Address from, Address[] to, Address[] cc, Address[] bcc, Address[] replyTo, Address sender, String subject, String body, String bodyType, BaseDatasource[] attachements) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.replyTo = replyTo;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
        this.bodyType = bodyType;
        this.attachements = attachements;
    }

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address[] getTo() {
        return to;
    }

    public void setTo(Address[] to) {
        this.to = to;
    }

    public Address[] getCc() {
        return cc;
    }

    public void setCc(Address[] cc) {
        this.cc = cc;
    }

    public Address[] getBcc() {
        return bcc;
    }

    public void setBcc(Address[] bcc) {
        this.bcc = bcc;
    }

    public Address[] getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Address[] replyTo) {
        this.replyTo = replyTo;
    }

    public Address getSender() {
        return sender;
    }

    public void setSender(Address sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public BaseDatasource[] getAttachements() {
        return attachements;
    }

    public void setAttachements(BaseDatasource[] attachements) {
        this.attachements = attachements;
    }
}
