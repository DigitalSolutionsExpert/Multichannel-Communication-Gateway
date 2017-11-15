package com.digitalsolutionsexpert.CustomerNotification.Application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.digitalsolutionsexpert.CustomerNotification.Datasource.BaseDatasource;
import com.digitalsolutionsexpert.CustomerNotification.Datasource.LocalFileDatasource;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.BaseSchedule;
import com.digitalsolutionsexpert.CustomerNotification.Schedule.ScheduleFactory;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.BaseServiceAdapterExecutionResult;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.BaseServiceAdapterRequest;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail.MailServiceAdapterRequest;
import com.digitalsolutionsexpert.CustomerNotification.Service.Adapter.Mail.MailServiceAdapter;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseService;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceInfo;
import com.digitalsolutionsexpert.CustomerNotification.Service.Template.Mail.MailTemplateService;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {

        /*
        if( args.length != 1 ) {
            System.out.println( "Usage: absolute/path/to/config.yml" );
            return;
        }
        */
        ObjectMapper objectMapper = new ObjectMapper();
        String configFileName = "C:\\Users\\lucian.dragomir\\IdeaProjects\\vdfcustomernotificationemail\\src\\resource\\config.yaml";
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration(configFileName);


        String[] serviceNames = new String[]{
                "service-polling-controller-01"
                //"service-handlebars-template-01"
                //, "service-mail-template-01"
                //,"service-mail-send-01"
        };
        BaseService services[] = new BaseService[serviceNames.length];
        for (int i = 0; i < serviceNames.length; i++) {
            services[i] = applicationConfiguration.getService(serviceNames[i]);
        }


        //MailTemplateService mailTemplateService = (MailTemplateService) applicationConfiguration.getService("service-mail-template-01");


        if (true) {
            return;
        }

        ScheduleFactory scheduleFactory = new ScheduleFactory();
        BaseSchedule baseSchedule = scheduleFactory.create(applicationConfiguration.getProperties().getConfigurationSubtree("service.service-monitor-01.monitor.[1].schedule"));
        System.out.println(baseSchedule);


//        HandlebarsTemplateService handlebarsTemplateService = (HandlebarsTemplateService) applicationConfiguration.getService("service-handlebars-template-01");
        //System.out.println(handlebarsTemplateService.toString());
        MailTemplateService mailTemplateService = (MailTemplateService) applicationConfiguration.getService("service-mail-template-01");
        //System.out.println(mailTemplateService.toString());
        MailServiceAdapter mailService = (MailServiceAdapter) applicationConfiguration.getService("service-mail-send-01");


        String payloadString = "" +
                "{\n" +
                "\t\"notifications\":{\n" +
                "\t\t\"emailTo\": [\n" +
                "\t\t\t{\"address\": \"iulian.nae@digitalsolutionsexpert.com\", \"name\" : \"Nae\"}\n" +
                "\t\t\t,{\"address\": \"lucian.dragomir@digitalsolutionsexpert.com\", \"name\" : \"Lucian DRAGOMIR VF\"}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"invoices\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"customerID\": 564586787,\n" +
                "\t\t\t\"BAN\": 1432232123,\t\n" +
                "\t\t\t\"invoiceNo\": \"VDF282802627\",\n" +
                "\t\t\t\"billCloseDate\": \"2017-08-17T00:00:00.000+03:00\",\n" +
                "\t\t\t\"billDueDate\": \t\"2017-08-31T00:00:00.000+03:00\",\n" +
                "\t\t\t\"totalDue\": 23.13,\n" +
                "\t\t\t\"fileReference\": \"/export/home/Download/PDF/281/08/20170817/234854281_20170817_1P.pdf\",\n" +
                "\t\t\t\"payUrl\": \"https://www.digitalsolutionsexpert.ro/myvodafone/plata-factura-online/index.htm?p=234854281VDF282802627\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
        List<Long> millis = new ArrayList();
        millis.add(System.currentTimeMillis());
        System.out.println(payloadString);
        Object mailRequest = null;
        BaseServiceAdapterExecutionResult mailResult = null;

        mailRequest = mailTemplateService.apply(1000000, payloadString);
        millis.add(System.currentTimeMillis());

        mailRequest = mailTemplateService.apply(1000000, payloadString);
        millis.add(System.currentTimeMillis());

        mailRequest = mailTemplateService.apply(1000000, payloadString);
        millis.add(System.currentTimeMillis());

        mailRequest = mailTemplateService.apply(1000000, payloadString);
        millis.add(System.currentTimeMillis());

        mailRequest = mailTemplateService.apply(1000000, payloadString);
        millis.add(System.currentTimeMillis());


        mailService.execute((BaseServiceAdapterRequest) mailRequest);
        millis.add(System.currentTimeMillis());
        mailService.execute((BaseServiceAdapterRequest) mailRequest);
        millis.add(System.currentTimeMillis());


        for (int i = 1; i < millis.size(); i++) {
            System.out.println("Duration interval [" + (i - 1) + "," + i + "] = " + (millis.get(i) - millis.get(i - 1)));
        }


        if (false) {
            Address internetAddress = new InternetAddress("MyVodafone.no-reply@digitalsolutionsexpert.ro");
            System.out.println(objectMapper.writeValueAsString(internetAddress));

            Address inteAddress2 = objectMapper.readValue("{\"address\":\"MyVodafone.no-reply@digitalsolutionsexpert.ro\"}", InternetAddress.class);
            System.out.println(objectMapper.writeValueAsString(inteAddress2));
        }

        if (false) {

            BaseServiceInfo sb1 = BaseServiceInfo.create(applicationConfiguration, "service-mail-template-01");
            System.out.println(sb1);
            BaseServiceInfo sb2 = BaseServiceInfo.create(applicationConfiguration, "service.service-mail-template-01.service.service-template-engine");
            System.out.println(sb2);
            BaseServiceInfo sb3 = BaseServiceInfo.create(applicationConfiguration, "service.service-mail-template-01.service.service-mail-template-persistence");
            System.out.println(sb3);


            String mailFileName = "C:\\Users\\lucian.dragomir\\IdeaProjects\\vdfcustomernotificationemail\\src\\resource\\index.html";

            MailServiceAdapter mailServiceAdapter = (MailServiceAdapter) applicationConfiguration.getService("service-mail-send-01");
            MailServiceAdapterRequest mailServiceAdapterRequest = new MailServiceAdapterRequest();

            Address mailFrom = new InternetAddress("MyVodafone.no-reply@digitalsolutionsexpert.ro");
            Address mailTO[] = new InternetAddress[1];
            mailTO[0] = new InternetAddress("lucian.dragomir@digitalsolutionsexpert.com");
            //mailTO[1] = new InternetAddress("gabriel.sandu@digitalsolutionsexpert.com");

            Address mailCC[] = null;
            Address mailBCC[] = null;
            Address mailReplyTO[] = null;
            Address mailSender = mailFrom;
            String mailSubject = "Test - factura ta a fost emisa";
            String mailBody = new String(Files.readAllBytes(Paths.get(mailFileName)), Charset.defaultCharset());
            String mailBodyType = "text/html; charset=utf-8";
            BaseDatasource[] mailAttachement;
            mailAttachement = new BaseDatasource[1];
            LocalFileDatasource localFileDatasource = new LocalFileDatasource(mailFileName);
            localFileDatasource.setAlias("ionel.html");
            mailAttachement[0] = localFileDatasource;

            mailServiceAdapterRequest.setFrom(mailFrom);
            mailServiceAdapterRequest.setTo(mailTO);
            mailServiceAdapterRequest.setSubject(mailSubject);
            mailServiceAdapterRequest.setBody(mailBody);
            mailServiceAdapterRequest.setBodyType(mailBodyType);

            //mailServiceAdapter.send(mailServiceAdapterRequest);

            /*
           */

            /*
            DataSource ds = applicationConfiguration.getDataSource("Database-notification-uat");
            Connection connection = ds.getConnection();
            connection.close();

            MailTemplateService configurationService = new MailTemplateService(applicationConfiguration, "service-configuration-app");

            DBExecutionService executionService = new DBExecutionService(applicationConfiguration, "srv-ex-default");


            List<MailServiceAdapterRequest> requestList = executionService.getRequestBatch();

            System.out.println( "Hello World!" );
            */

        }

    }
}
