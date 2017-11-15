package com.digitalsolutionsexpert.CustomerNotification.DataValidator;

public interface IDataValidator {

    public DataValidationReport validate(Object data);

    public boolean isValid();

    public Object convertToProcessingFormat(Object payload) throws Exception;

    public Object convertToValidationFormat(Object payload) throws Exception;

}
