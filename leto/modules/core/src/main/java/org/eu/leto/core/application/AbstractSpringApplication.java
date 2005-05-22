package org.eu.leto.core.application;


import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.CompositeMessageRegister;
import org.eu.leto.core.CompositeObjectRegister;
import org.eu.leto.core.MessageRegister;
import org.eu.leto.core.ObjectRegister;
import org.eu.leto.core.SpringMessageRegister;
import org.eu.leto.core.SpringObjectRegister;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public abstract class AbstractSpringApplication extends AbstractApplication
        implements ApplicationContextAware {
    private final Log log = LogFactory.getLog(getClass());
    private ApplicationContext applicationContext;
    private final String[] additionalConfigLocations;


    public AbstractSpringApplication(final String id,
            final String[] additionalConfigLocations) {
        super(id);
        if (additionalConfigLocations == null) {
            throw new NullArgumentException("additionalConfigLocations");
        }
        this.additionalConfigLocations = additionalConfigLocations;
    }


    public AbstractSpringApplication(final String id) {
        this(id, new String[0]);
    }


    public final void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public URL getResource(String path) {
        if(path == null) {
            return null;
        }
        try {
            return applicationContext.getResource(path).getURL();
        } catch (Exception e) {
            log.error("Error while loading resource: " + path, e);
            return null;
        }
    }


    @Override
    protected final void onDispose() throws Exception {
        if (applicationContext instanceof AbstractApplicationContext) {
            log.debug("Closing application context");
            final AbstractApplicationContext appContext = (AbstractApplicationContext) applicationContext;
            appContext.close();
        }
        applicationContext = null;

        setObjectRegister(null);
        setMessageRegister(null);
    }


    @Override
    protected final void onInit() throws Exception {
        if (applicationContext == null) {
            throw new IllegalStateException(
                    "No application context available: is this object declared in a Spring application context ?");
        }

        if (additionalConfigLocations.length > 0) {
            if (log.isDebugEnabled()) {
                log
                        .debug("Loading additional application context from config locations: "
                                + Arrays.asList(additionalConfigLocations));
            }
            final ApplicationContext additionalContext = new ClassPathXmlApplicationContext(
                    additionalConfigLocations, applicationContext);

            final ObjectRegister[] objectRegisters = {
                    new SpringObjectRegister(additionalContext),
                    new SpringObjectRegister(applicationContext), };
            final MessageRegister[] messageRegisters = {
                    new SpringMessageRegister(additionalContext),
                    new SpringMessageRegister(applicationContext), };

            setObjectRegister(new CompositeObjectRegister(Arrays
                    .asList(objectRegisters)));
            setMessageRegister(new CompositeMessageRegister(Arrays
                    .asList(messageRegisters)));
        } else {
            setObjectRegister(new SpringObjectRegister(applicationContext));
            setMessageRegister(new SpringMessageRegister(applicationContext));
        }
    }
}
