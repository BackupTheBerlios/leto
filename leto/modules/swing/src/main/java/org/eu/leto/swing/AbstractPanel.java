package org.eu.leto.swing;


import javax.swing.JPanel;

import org.apache.commons.lang.NullArgumentException;
import org.eu.leto.core.application.Application;


public class AbstractPanel extends JPanel {
    private final Application application;
    private final SwingServices applicationServices;


    public AbstractPanel(final Application application) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        this.application = application;
        this.applicationServices = new SwingServices(application);
    }


    protected final SwingServices getApplicationServices() {
        return applicationServices;
    }


    protected final Application getApplication() {
        return application;
    }
}
