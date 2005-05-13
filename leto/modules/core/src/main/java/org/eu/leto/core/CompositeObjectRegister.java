package org.eu.leto.core;


import java.util.Collection;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CompositeObjectRegister extends AbstractObjectRegister {
    private final Collection<ObjectRegister> objectRegistries;
    private final Log log = LogFactory.getLog(getClass());


    public CompositeObjectRegister(
            final Collection<ObjectRegister> objectRegisters) {
        super();
        if (objectRegisters == null) {
            throw new NullArgumentException("objectRegisters");
        }
        this.objectRegistries = objectRegisters;
    }


    @Override
    protected Object getBeanInternal(String name) {
        for (final ObjectRegister objectRegister : objectRegistries) {
            final Object bean = objectRegister.getBean(name);
            if (bean != null) {
                return bean;
            }
        }

        return null;
    }


    @Override
    protected Object getBeanOfTypeInternal(Class clazz) {
        for (final ObjectRegister objectRegister : objectRegistries) {
            final Object bean = objectRegister.getBeanOfType(clazz);
            if (bean != null) {
                return bean;
            }
        }

        return null;
    }
}
