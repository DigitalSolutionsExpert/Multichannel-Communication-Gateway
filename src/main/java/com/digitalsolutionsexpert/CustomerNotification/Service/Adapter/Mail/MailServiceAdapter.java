package com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.*;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.nlab.smtp.pool.SmtpConnectionPool;
import org.nlab.smtp.transport.connection.ClosableSmtpConnection;
import org.nlab.smtp.transport.factory.SmtpConnectionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.lang.invoke.MethodHandles;
import java.util.Objects;
import java.util.Properties;

public class MailServiceAdapter extends BaseServiceAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    SmtpConnectionPool smtpConnectionPool;

    public MailServiceAdapter(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException {
        super(applicationConfiguration, path, name);
        this.smtpConnectionPool = this.createSmtpConnectionPool();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            smtpConnectionPool.close();
        } finally {
            super.finalize();
        }
    }

    @Override
    public BaseServiceAdapterExecutionResult execute(BaseServiceAdapterRequest request) {
        BaseServiceAdapterExecutionStatus executionStatus = null;
        Objects.requireNonNull(request, "MailServiceAdapterRequest nust not be null");

        try {
            this.sendMail((MailServiceAdapterRequest) request);
            executionStatus = (BaseServiceAdapterExecutionStatus) BaseServiceAdapterExecutionStatus.createFrom(null);
        } catch (MailServiceAdapterException e) {
            executionStatus = (BaseServiceAdapterExecutionStatus) BaseServiceAdapterExecutionStatus.createFrom(e);
        }
        return new BaseServiceAdapterExecutionResult(null, executionStatus);
    }

    @Override
    public void onReceive(BaseServiceAdapterExecutionResult executionStatus) {
        //throw NotImplementedException("Method onReceive of class " + this.getClass().getSimpleName() + " not implemented");
    }

    private SmtpConnectionPool createSmtpConnectionPool() {
        SmtpConnectionPool connectionPool = null;
        SmtpConnectionFactoryBuilder smtpConnectionFactoryBuilder = null;
        GenericObjectPoolConfig smtpPool = null;
        final Properties smtpSessionProperties = this.getProperties().getConfigurationSubtree("smtp.properties").toProperties("", "-", ".");
        final Properties smtpAuthenticatorProperties = this.getProperties().getConfigurationSubtree("smtp.authenticator").toProperties("", "-", ".");
        final Properties smtpPoolProperties = this.getProperties().getConfigurationSubtree("pool").toProperties(null, null, null);

        if (smtpSessionProperties.containsKey("mail.smtp.auth") && smtpSessionProperties.getProperty("mail.smtp.auth").equalsIgnoreCase("true")) {
            javax.mail.Authenticator smtpAuthenticator = new javax.mail.Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            smtpAuthenticatorProperties.getProperty("mail.smtp.user"),
                            smtpAuthenticatorProperties.getProperty("mail.smtp.password")
                    );

                }

            };
            smtpConnectionFactoryBuilder = SmtpConnectionFactoryBuilder.newSmtpBuilder().session(smtpSessionProperties, smtpAuthenticator);
        } else {
            smtpConnectionFactoryBuilder = SmtpConnectionFactoryBuilder.newSmtpBuilder().session(smtpSessionProperties, null);
        }
        smtpPool = com.digitalsolutionsexpert.CustomerNotification.Utils.PoolUtils.getGenericObjectPoolConfig(smtpPoolProperties);
        connectionPool = new SmtpConnectionPool(smtpConnectionFactoryBuilder.build(), smtpPool);
        return connectionPool;
    }

    private void sendMail(MailServiceAdapterRequest mailServiceAdapterRequest) throws MailServiceAdapterException {
        if (!smtpConnectionPool.isClosed()) {
            ClosableSmtpConnection smtpConnection = null;
            try {
                smtpConnection = this.smtpConnectionPool.borrowObject();
                MimeMessage mimeMessage = new MimeMessage(smtpConnection.getSession());
                //set from
                if (mailServiceAdapterRequest.getFrom() != null) {
                    mimeMessage.setFrom(mailServiceAdapterRequest.getFrom());
                }
                //set to
                if (mailServiceAdapterRequest.getTo() != null && mailServiceAdapterRequest.getTo().length > 0) {
                    mimeMessage.setRecipients(Message.RecipientType.TO, mailServiceAdapterRequest.getTo());
                }
                //set cc
                if (mailServiceAdapterRequest.getCc() != null && mailServiceAdapterRequest.getCc().length > 0) {
                    mimeMessage.setRecipients(Message.RecipientType.CC, mailServiceAdapterRequest.getCc());
                }
                //set bcc
                if (mailServiceAdapterRequest.getBcc() != null && mailServiceAdapterRequest.getBcc().length > 0) {
                    mimeMessage.setRecipients(Message.RecipientType.BCC, mailServiceAdapterRequest.getBcc());
                }
                //set replyto
                if (mailServiceAdapterRequest.getReplyTo() != null && mailServiceAdapterRequest.getReplyTo().length > 0) {
                    mimeMessage.setReplyTo(mailServiceAdapterRequest.getReplyTo());
                }
                //set sender
                if (mailServiceAdapterRequest.getSender() != null) {
                    mimeMessage.setSender(mailServiceAdapterRequest.getSender());
                }
                //set subject
                mimeMessage.setSubject(mailServiceAdapterRequest.getSubject());

                Multipart multipart = new MimeMultipart();
                //send body part
                BodyPart htmlBodyPart = new MimeBodyPart();
                htmlBodyPart.setContent(mailServiceAdapterRequest.getBody(), mailServiceAdapterRequest.getBodyType());
                multipart.addBodyPart(htmlBodyPart);
                //send attachements
                if (mailServiceAdapterRequest.getAttachements() != null && mailServiceAdapterRequest.getAttachements().length > 0) {
                    for (int i = 0; i < mailServiceAdapterRequest.getAttachements().length; i++) {
                        MailServiceAdapter.addAtachement(multipart, mailServiceAdapterRequest.getAttachements()[i], mailServiceAdapterRequest.getAttachements()[i].getAlias());
                    }
                }
                mimeMessage.setContent(multipart);
                //send message
                smtpConnection.sendMessage(mimeMessage);

            } catch (MessagingException e) {
                throw new MailServiceAdapterException(e);
            } catch (Exception e) {
                throw new MailServiceAdapterException(e);
            } finally {
                if (smtpConnection != null) {
                    smtpConnectionPool.returnObject(smtpConnection);
                }
            }
        } else {
            throw new MailServiceAdapterException("MailServiceAdapter connection pool is closed.");
        }
    }

    private static void addAtachement(Multipart multipart, DataSource dataSource, String fileName) throws MessagingException {
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(dataSource));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);
    }
}
