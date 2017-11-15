package com.digitalsolutionsexpert.CustomerNotification.DataValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DataValidatorFactory {

    public IDataValidator create(String validator, String type) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        IDataValidator dataValidator = null;
        String clazzName = (type==null) ? "com.digitalsolutionsexpert.CustomerNotification.DataValidator.JsonDataValidator" : type;
        Class<IDataValidator> clazz = (Class<IDataValidator>) Class.forName(clazzName);
        Constructor<IDataValidator> constructor = clazz.getDeclaredConstructor(String.class);
        dataValidator = constructor.newInstance(validator);
        return dataValidator;
    }
}
