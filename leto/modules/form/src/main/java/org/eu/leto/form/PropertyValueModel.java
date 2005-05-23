package org.eu.leto.form;


import ognl.Ognl;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.eu.leto.core.application.Application;
import org.eu.leto.core.application.ApplicationException;

import java.beans.PropertyChangeListener;


class PropertyValueModel implements ValueModel, PropertyChangePublisher {
    public static final String DISABLED_PROPERTY = "disabled";
    public static final String READONLY_PROPERTY = "readOnly";

    private final Object bean;
    private final PropertyChangePublisherSupport propertyChangePublisherSupport;
    private final String propertyName;
    private boolean readOnly;
    private boolean disabled;
    private final Application application;


    public PropertyValueModel(final Application application, final Object bean,
            final String propertyName) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        if (bean == null) {
            throw new NullArgumentException("bean");
        }
        if (StringUtils.isBlank(propertyName)) {
            throw new IllegalArgumentException("propertyName is required");
        }
        this.application = application;
        this.bean = bean;
        this.propertyName = propertyName;

        propertyChangePublisherSupport = new PropertyChangePublisherSupport(
                bean);
    }


    public void setValue(Object value) {
        if (isReadOnly()) {
            throw new ApplicationException(application, null,
                    "error.value.readOnly", propertyName, bean);
        }

        try {
            final Object oldValue = getValue();
            Ognl.setValue(propertyName, bean, value);
            propertyChangePublisherSupport.firePropertyChanged(propertyName,
                    oldValue, value);
        } catch (Exception e) {
            throw new ApplicationException(application, e,
                    "error.value.setValue", value, propertyName, bean);
        }
    }


    public Object getValue() {
        try {
            return Ognl.getValue(propertyName, bean);
        } catch (Exception e) {
            throw new ApplicationException(application, e,
                    "error.value.getValue", propertyName, bean);
        }
    }


    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangePublisherSupport.addPropertyChangeListener(l);
    }


    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangePublisherSupport.removePropertyChangeListener(l);
    }


    public boolean isDisabled() {
        return disabled;
    }


    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }


    public boolean isReadOnly() {
        return readOnly;
    }


    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

}
