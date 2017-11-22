package com.digitalsolutionsexpert.CustomerNotification.Application;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseService;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;


public class App {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws Exception {
        ApplicationConfiguration applicationConfiguration;
        String loggerConfigFile;
        if (args.length < 2) {
            System.out.println("executable {absolute/path/to/applicationconfig.yml} {absolute/path/to/logging.properties} {service to start} [{service to start} ...]");
            return;
        }
        applicationConfiguration = new ApplicationConfiguration(args[0]);
        //System.setProperty("log4j.configurationFile", args[1]);
        //System.setProperty("log4j.configuration", args[1]);

        //LogManager.getLogManager().readConfiguration(new FileInputStream(args[1]));

        logger.info("Starting gateway ...");
        logger.info("Use [" + args[0] + "] application configuration file");
        //logger.info("Use [" + args[1] + "] logging configuration file");

        logger.info("Starting services ...");
        int servicesOffset = 1;
        BaseService services[] = new BaseService[args.length - servicesOffset];
        for (int i = 0; i < args.length - servicesOffset; i++) {
            String serviceName = args[i + servicesOffset];
            logger.info("Creating service instance for [" + serviceName + "]");
            services[i] = applicationConfiguration.getService(serviceName);
            logger.info("Created service instance for [" + serviceName + "] with instance [" + services[i] + "]");
        }
        logger.info("Started gateway");

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        }

    }
}
