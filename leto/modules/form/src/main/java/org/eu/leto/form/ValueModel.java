package org.eu.leto.form;


import java.beans.PropertyChangeListener;


public interface ValueModel {
    void setValue(Object value);


    Object getValue();


    boolean isReadOnly();


    boolean isDisabled();


    void setReadOnly(boolean readOnly);


    void setDisabled(boolean disabled);


    void addPropertyChangeListener(PropertyChangeListener l);


    void removePropertyChangeListener(PropertyChangeListener l);
}
