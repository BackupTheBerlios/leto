package org.eu.leto.core.application;


import java.net.URL;
import java.util.List;
import java.util.prefs.Preferences;

import org.eu.leto.core.MessageRegister;
import org.eu.leto.core.ObjectRegister;


public interface Application {
    void setApplicationConfigurers(List<ApplicationConfigurer> list);


    String getId();


    MessageRegister getMessageRegister();


    String getMessage(String key, Object... args);


    Object getBean(String name);


    Object getBeanOfType(Class clazz);


    ObjectRegister getObjectRegister();


    Preferences getPreferences();


    URL getResource(String path);


    void dispose();


    void fatal(String msg, Throwable e);


    void init();


    void raiseException(Exception e, String key, Object... args);


    void start();


    void stop();
}
