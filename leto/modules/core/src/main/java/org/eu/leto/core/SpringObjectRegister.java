package org.eu.leto.core;


import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;


public class SpringObjectRegister extends AbstractObjectRegister {
    private final ApplicationContext applicationContext;
    private final Log log = LogFactory.getLog(getClass());


    public SpringObjectRegister(final ApplicationContext applicationContext) {
        super();
        if (applicationContext == null) {
            throw new NullArgumentException("applicationContext");
        }
        this.applicationContext = applicationContext;
    }


    @Override
    protected Object getBeanInternal(String name) {
        return applicationContext.getBean(name);
    }


    @Override
    @SuppressWarnings("unchecked")
    protected Object getBeanOfTypeInternal(Class clazz) {
        final Map<String, Object> map = (Map<String, Object>) applicationContext
                .getBeansOfType(clazz);
        if (map.isEmpty()) {
            return null;
        }

        final Map.Entry<String, Object> entry = map.entrySet().iterator()
                .next();
        final String name = entry.getKey();

        if (map.size() > 1) {
            if (log.isWarnEnabled()) {
                log.warn("Several beans of type " + clazz.getName()
                        + " found: returning bean named " + name);
            }
        }

        return entry.getValue();
    }
}
