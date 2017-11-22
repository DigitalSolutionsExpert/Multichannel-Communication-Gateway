package com.digitalsolutionsexpert.CustomerNotification.DataValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public abstract class BaseDataValidator implements IDataValidator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String validator;

    public BaseDataValidator(String validator) {
        this.validator = validator;
    }

}
