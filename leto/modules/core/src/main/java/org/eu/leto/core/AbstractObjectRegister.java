package org.eu.leto.core;


import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractObjectRegister implements ObjectRegister {
    private final Log log = LogFactory.getLog(getClass());


    public Object getBean(String name, boolean required) {
        if (name == null) {
            throw new NullArgumentException("name");
        }

        if (log.isDebugEnabled()) {
            log.debug("Getting object named '" + name + "'");
        }

        Object bean = null;
        try {
            bean = getBeanInternal(name);
        } catch (Exception e) {
            log.warn("Error while getting object named '" + name + "'", e);
        }

        if (bean == null) {
            if (log.isWarnEnabled()) {
                log.warn("Null value returned for object named '" + name + "'");
            }
        }

        if (required && (bean == null)) {
            throw new IllegalStateException("Bean named '" + name
                    + "' is required");
        }

        return bean;
    }


    public final Object getBean(String name) {
        return getBean(name, true);
    }


    public Object getBeanOfType(Class clazz, boolean required) {
        if (clazz == null) {
            throw new NullArgumentException("clazz)");
        }

        if (log.isDebugEnabled()) {
            log.debug("Getting object of type '" + clazz.getName() + "'");
        }

        Object bean = null;
        try {
            bean = getBeanOfTypeInternal(clazz);
        } catch (Exception e) {
            log.warn("Error while getting object of type '" + clazz.getName()
                    + "'", e);
        }

        if (bean == null) {
            if (log.isWarnEnabled()) {
                log.warn("Null value returned for object of type '"
                        + clazz.getName() + "'");
            }
        }

        if (required && (bean == null)) {
            throw new IllegalStateException("Bean of type '" + clazz.getName()
                    + "' is required");
        }

        return bean;
    }


    public final Object getBeanOfType(Class clazz) {
        return getBeanOfType(clazz, true);
    }


    protected abstract Object getBeanInternal(String name);


    protected abstract Object getBeanOfTypeInternal(Class clazz);
}
