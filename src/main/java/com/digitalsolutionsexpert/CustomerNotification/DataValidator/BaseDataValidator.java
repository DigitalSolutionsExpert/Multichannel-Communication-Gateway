package com.digitalsolutionsexpert.CustomerNotification.DataValidator;

public abstract class BaseDataValidator implements IDataValidator {
    private String validator;

    public BaseDataValidator(String validator) {
        this.validator = validator;
    }

}
