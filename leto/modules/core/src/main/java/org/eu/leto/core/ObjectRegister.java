package org.eu.leto.core;

public interface ObjectRegister {
    Object getBean(String name);


    Object getBean(String name, boolean required);


    Object getBeanOfType(Class clazz);


    Object getBeanOfType(Class clazz, boolean required);
}
