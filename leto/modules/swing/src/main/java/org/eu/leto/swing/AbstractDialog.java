package org.eu.leto.swing;


import java.awt.Frame;

import javax.swing.JDialog;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.eu.leto.core.application.Application;
import org.eu.leto.core.application.ApplicationComponent;


public abstract class AbstractDialog extends JDialog implements
        ApplicationComponent {
    private final Application application;
    private final SwingServices services;
    private final String id;


    public AbstractDialog(final Application application, final Frame owner, final String id) {
        super(owner);
        if (application == null) {
            throw new NullArgumentException("application");
        }
        if (StringUtils.isBlank(id)) {
            throw new NullArgumentException("id");
        }
        this.application = application;
        this.id = id;
        this.services = new SwingServices(
                application);

        init();
    }


    public final Application getApplication() {
        return application;
    }


    public final String getId() {
        return id;
    }


    private void init() {
        final String title = getApplication().getMessage(getId());
        if (title != null) {
            setTitle(title);
        }
        setModal(true);
    }


    protected final SwingServices getServices() {
        return services;
    }
}
