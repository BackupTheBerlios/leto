package org.eu.leto.form;


import java.beans.PropertyChangeListener;

import ognl.Ognl;

import org.eu.leto.core.AbstractCache;
import org.eu.leto.core.application.AbstractApplicationComponent;
import org.eu.leto.core.application.Application;
import org.eu.leto.core.application.ApplicationException;


public class FormModel<T> extends AbstractApplicationComponent {
    public static final String DISABLED_PROPERTY = "disabled";
    public static final String READONLY_PROPERTY = "readOnly";

    private final FormModelPropertyDescriptorCache formModelPropertyDescriptorCache = new FormModelPropertyDescriptorCache();
    private T formObject;
    private final PropertyChangePublisherSupport propertyChangePublisherSupport = new PropertyChangePublisherSupport(
            this);
    private final ValueModelCache valueModelCache = new ValueModelCache();
    private boolean disabled;
    private boolean readOnly;


    public FormModel(final Application application, final String id,
            final T formObject) {
        super(application, id);
        setFormObject(formObject);
    }


    public void setDisabled(boolean b) {
        propertyChangePublisherSupport.firePropertyChanged(DISABLED_PROPERTY,
                this.disabled, b);
        this.disabled = b;
    }


    public boolean isDisabled() {
        return disabled;
    }


    public void setFormObject(T obj) {
        if (isReadOnly()) {
            throw new ApplicationException(getApplication(), null,
                    "error.form.readOnly", getId());
        }
        this.formObject = obj;
    }


    public T getFormObject() {
        return formObject;
    }


    public FormModelPropertyDescriptor getPropertyDescriptor(String path) {
        return formModelPropertyDescriptorCache
                .getFormModelPropertyDescriptor(path);
    }


    public void setReadOnly(boolean b) {
        propertyChangePublisherSupport.firePropertyChanged(READONLY_PROPERTY,
                this.readOnly, b);
        this.readOnly = b;
    }


    public boolean isReadOnly() {
        return readOnly;
    }


    public Object getValue(String path) {
        try {
            return Ognl.getValue(path, getFormObject());
        } catch (Exception e) {
            throw new ApplicationException(getApplication(), e,
                    "error.form.getValue", new Object[] { path, getId() });
        }
    }


    public ValueModel getValueModel(String path) {
        return valueModelCache.getValueModel(path);
    }


    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangePublisherSupport.addPropertyChangeListener(l);
    }


    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangePublisherSupport.removePropertyChangeListener(l);
    }

    private class FormModelPropertyDescriptorCache extends
            AbstractCache<FormModelPropertyDescriptor> {
        public FormModelPropertyDescriptor getFormModelPropertyDescriptor(
                String path) {
            return (FormModelPropertyDescriptor) getObject(path);
        }


        protected FormModelPropertyDescriptor loadObject(String key) {
            return new FormModelPropertyDescriptor(getApplication(), getId()
                    + "." + key);
        }
    }

    private class ValueModelCache extends AbstractCache<ValueModel> {
        public ValueModel getValueModel(String path) {
            return (ValueModel) getObject(path);
        }


        protected ValueModel loadObject(String key) {
            return new PropertyValueModel(getApplication(), getFormObject(),
                    key);
        }
    }
}
