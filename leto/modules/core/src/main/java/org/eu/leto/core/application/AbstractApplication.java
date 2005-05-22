package org.eu.leto.core.application;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.MessageRegister;
import org.eu.leto.core.ObjectRegister;


public abstract class AbstractApplication implements Application {
    private final Log log = LogFactory.getLog(getClass());
    private MessageRegister messageRegister;
    private ObjectRegister objectRegister;
    private final Preferences preferences;
    private final String id;
    private ApplicationMessageHelper applicationMessageHelper;
    private List<ApplicationConfigurer> applicationConfigurers = new ArrayList<ApplicationConfigurer>();


    public AbstractApplication(final String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id is required");
        }
        this.id = id;

        preferences = Preferences.userRoot().node(getId());
    }


    public void setApplicationConfigurers(
            List<ApplicationConfigurer> applicationConfigurers) {
        this.applicationConfigurers = applicationConfigurers;
    }


    public final String getId() {
        return id;
    }


    public void setApplicationMessageHelper(
            ApplicationMessageHelper applicationMessageHelper) {
        this.applicationMessageHelper = applicationMessageHelper;
    }


    public MessageRegister getMessageRegister() {
        return messageRegister;
    }


    public void setObjectRegister(ObjectRegister objectRegister) {
        this.objectRegister = objectRegister;
    }


    public ObjectRegister getObjectRegister() {
        return objectRegister;
    }


    public Preferences getPreferences() {
        return preferences;
    }


    public URL getResource(String path) {
        return getClass().getResource(path);
    }


    public Object getBean(String name) {
        return getObjectRegister().getBean(name);
    }


    public Object getBeanOfType(Class clazz) {
        return getObjectRegister().getBeanOfType(clazz);
    }


    public String getMessage(String key, Object... args) {
        return getMessageRegister().getMessage(key, args);
    }


    public void setUseMessageHelper(boolean b) {
        if (!b) {
            applicationMessageHelper = null;
        } else
            if (applicationMessageHelper == null) {
                applicationMessageHelper = new ApplicationMessageHelper(this);
            }
    }


    public final void dispose() {
        log.info("Disposing application: " + getId());
        try {
            fireApplicationConfigurerOnDispose();

            objectRegister = null;
            messageRegister = null;

            onDispose();
            ApplicationRegister.getInstance().unregisterApplication(this);
            System.exit(0);
        } catch (Exception e) {
            raiseException(e, "error.application.dispose");
        }
    }


    public void fatal(String msg, Throwable e) {
        log.fatal("Fatal error: exiting application '" + getId() + "'", e);
        System.exit(1);
    }


    public final void init() {
        log.info("Initializing application: " + getId());
        try {
            fireApplicationConfigurerOnInit();

            onInit();
            ApplicationRegister.getInstance().registerApplication(this);
        } catch (Exception e) {
            raiseException(e, "error.application.init");
        }
    }


    public final void raiseException(Exception e, String key, Object... args) {
        final String msg;
        if (getMessageRegister() == null) {
            msg = key;
        } else {
            // we construct a localized message
            msg = getMessageRegister().getMessage(key, args);
        }

        final ApplicationException exc = new ApplicationException(this, e, key,
                args);
        if (applicationMessageHelper != null) {
            applicationMessageHelper.showErrorMessage(null, key, getId(), exc,
                    args);
        }
        throw exc;
    }


    public final void start() {
        log.info("Starting application: " + getId());
        try {
            fireApplicationConfigurerOnStart();
            onStart();
        } catch (Exception e) {
            raiseException(e, "error.application.start");
        }
    }


    public final void stop() {
        log.info("Stopping application: " + getId());
        try {
            fireApplicationConfigurerOnStop();
            onStop();
        } catch (Exception e) {
            raiseException(e, "error.application.stop");
        }
    }


    public void setMessageRegister(MessageRegister messageRegister) {
        this.messageRegister = messageRegister;
    }


    protected abstract void onDispose() throws Exception;


    protected abstract void onInit() throws Exception;


    protected abstract void onStart() throws Exception;


    protected abstract void onStop() throws Exception;


    private void fireApplicationConfigurerOnDispose() {
        for (final ApplicationConfigurer ac : applicationConfigurers) {
            ac.onDispose(this);
        }
    }


    private void fireApplicationConfigurerOnInit() {
        for (final ApplicationConfigurer ac : applicationConfigurers) {
            ac.onInit(this);
        }
    }


    private void fireApplicationConfigurerOnStart() {
        for (final ApplicationConfigurer ac : applicationConfigurers) {
            ac.onStart(this);
        }
    }


    private void fireApplicationConfigurerOnStop() {
        for (final ApplicationConfigurer ac : applicationConfigurers) {
            ac.onStop(this);
        }
    }
}
