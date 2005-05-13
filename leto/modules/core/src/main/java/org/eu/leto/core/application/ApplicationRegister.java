package org.eu.leto.core.application;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class ApplicationRegister {
    private static final ApplicationRegister instance = new ApplicationRegister();

    private final Log log = LogFactory.getLog(getClass());
    private final Map<String, Application> applications = new HashMap<String, Application>(
            1);


    private ApplicationRegister() {
    }


    public synchronized static ApplicationRegister getInstance() {
        return instance;
    }


    public synchronized Application getApplication(String id) {
        if (id == null) {
            throw new NullArgumentException("id");
        }

        final Application app = applications.get(id);
        if (app == null) {
            throw new IllegalStateException("No such application registered: "
                    + id);
        }

        return app;
    }


    public synchronized boolean isApplicationRegistered(String id) {
        return applications.containsKey(id);
    }


    public synchronized void registerApplication(Application application) {
        if (isApplicationRegistered(application.getId())) {
            if (log.isWarnEnabled()) {
                log.warn("Replacing application with new one: "
                        + application.getId());
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Registering application: " + application.getId());
        }
        applications.put(application.getId(), application);
    }


    public synchronized void unregisterApplication(Application application) {
        applications.remove(application);
    }
}
