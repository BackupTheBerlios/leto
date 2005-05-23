package org.eu.leto.form;


import java.beans.PropertyChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.ActionDescriptor;
import org.eu.leto.core.application.AbstractApplicationComponent;
import org.eu.leto.core.application.Application;


public class FormModelPropertyDescriptor extends AbstractApplicationComponent
        implements PropertyChangePublisher {
    public static final String DISABLED_PROPERTY = "disabled";
    public static final String READONLY_PROPERTY = "readOnly";

    private final Log log = LogFactory.getLog(getClass());
    private final PropertyChangePublisherSupport propertyChangePublisherSupport = new PropertyChangePublisherSupport(
            this);
    private boolean disabled;
    private boolean readOnly;


    public FormModelPropertyDescriptor(final Application application,
            final String id) {
        super(application, id);

        disabled = application.getMessageRegister().getBooleanMessage(
                getId() + ".disabled");
        readOnly = application.getMessageRegister().getBooleanMessage(
                getId() + ".readOnly");
    }


    public String getDescription() {
        return getApplication().getMessage(getId() + ".desc");
    }


    public void setDisabled(boolean b) {
        propertyChangePublisherSupport.firePropertyChanged(DISABLED_PROPERTY,
                this.disabled, b);
        this.disabled = b;
    }


    public boolean isDisabled() {
        return disabled;
    }


    public String getImage() {
        return getApplication().getMessage(getId() + ".icon");
    }


    public ActionDescriptor getActionDescriptor() {
        if (log.isDebugEnabled()) {
            log.debug("Creating ActionDescriptor for property '" + getId()
                    + "'");
        }

        return ActionDescriptor.parse(getApplication().getMessage(getId()));
    }


    public void setReadOnly(boolean b) {
        propertyChangePublisherSupport.firePropertyChanged(READONLY_PROPERTY,
                this.readOnly, b);
        this.readOnly = b;
    }


    public boolean isReadOnly() {
        return readOnly;
    }


    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangePublisherSupport.addPropertyChangeListener(l);
    }


    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangePublisherSupport.removePropertyChangeListener(l);
    }
}
