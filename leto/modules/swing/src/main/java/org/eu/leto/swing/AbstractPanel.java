package org.eu.leto.swing;


import javax.swing.JPanel;

import org.apache.commons.lang.NullArgumentException;
import org.eu.leto.core.application.Application;


public class AbstractPanel extends JPanel {
    private final Application application;
    private final SwingServices services;


    public AbstractPanel(final Application application) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        this.application = application;
        this.services = new SwingServices(application);
    }


    protected final SwingServices getServices() {
        return services;
    }


    protected final Application getApplication() {
        return application;
    }
}
