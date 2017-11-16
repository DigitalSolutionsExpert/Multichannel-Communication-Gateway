package com.digitalsolutionsexpert.CustomerNotification.Application;

import com.digitalsolutionsexpert.CustomerNotification.Service.BaseService;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class App {
    static Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        ApplicationConfiguration applicationConfiguration;
        String loggerConfigFile;
        if (args.length < 3) {
            System.out.println("executable {absolute/path/to/applicationconfig.yml} {absolute/path/to/logging.properties} {service to start} [{service to start} ...]");
            return;
        }
        applicationConfiguration = new ApplicationConfiguration(args[0]);
        LogManager.getLogManager().readConfiguration(new FileInputStream(args[1]));

        logger.log(Level.INFO, "Starting gateway ...");
        logger.log(Level.INFO, "Use [" + args[0] + "] application configuration file");
        logger.log(Level.INFO, "Use [" + args[1] + "] logging configuration file");

        logger.log(Level.INFO, "Starting services ...");
        int servicesOffset = 2;
        BaseService services[] = new BaseService[args.length - servicesOffset];
        for (int i = 0; i < args.length - servicesOffset; i++) {
            String serviceName = args[i + servicesOffset];
            logger.log(Level.INFO, "Creating service instance for [" + serviceName + "]");
            services[i] = applicationConfiguration.getService(serviceName);
            logger.log(Level.INFO, "Created service instance for [" + serviceName + "] with instance [" + services[i] + "]");
        }
        logger.log(Level.INFO, "Started gateway");

    }
}
