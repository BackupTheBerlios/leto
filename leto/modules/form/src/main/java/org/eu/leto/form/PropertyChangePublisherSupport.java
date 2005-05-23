package org.eu.leto.form;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;


public class PropertyChangePublisherSupport implements PropertyChangePublisher {
    private final List<PropertyChangeListener> propertyChangeListeners = new ArrayList<PropertyChangeListener>(
            1);
    private final Object source;


    public PropertyChangePublisherSupport(final Object source) {
        if (source == null) {
            throw new NullArgumentException("source");
        }
        this.source = source;
    }


    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeListeners.add(l);
    }


    public void firePropertyChanged(String propertyName, boolean oldValue,
            boolean newValue) {
        firePropertyChanged(propertyName, Boolean.valueOf(oldValue), Boolean
                .valueOf(newValue));
    }


    public void firePropertyChanged(String propertyName, Object oldValue,
            Object newValue) {
        final PropertyChangeEvent e = new PropertyChangeEvent(source,
                propertyName, oldValue, newValue);

        for (final PropertyChangeListener listener : propertyChangeListeners) {
            listener.propertyChange(e);
        }
    }


    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeListeners.remove(l);
    }
}
