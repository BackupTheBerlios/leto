package org.eu.leto.form;


import java.beans.PropertyChangeListener;


public interface PropertyChangePublisher {
    void addPropertyChangeListener(PropertyChangeListener l);


    void removePropertyChangeListener(PropertyChangeListener l);
}
