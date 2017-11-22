package com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail;

import com.digitalsolutionsexpert.CustomerNotification.Datasource.BaseDatasource;

import javax.mail.Address;

public class MailServiceAdapterResponse extends MailServiceAdapterRequest {

    private String messageId;

    public MailServiceAdapterResponse() {
    }

    public MailServiceAdapterResponse(Address from, Address[] to, Address[] cc, Address[] bcc, Address[] replyTo, Address sender, String subject, String body, String bodyType, BaseDatasource[] attachements, String messageId) {
        super(from, to, cc, bcc, replyTo, sender, subject, body, bodyType, attachements);
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
