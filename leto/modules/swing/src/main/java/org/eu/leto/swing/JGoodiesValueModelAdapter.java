package org.eu.leto.swing;


import java.beans.PropertyChangeListener;

import org.apache.commons.lang.NullArgumentException;
import org.eu.leto.form.ValueModel;


class JGoodiesValueModelAdapter implements
        com.jgoodies.binding.value.ValueModel {
    private final ValueModel valueModel;


    public JGoodiesValueModelAdapter(final ValueModel valueModel) {
        if (valueModel == null) {
            throw new NullArgumentException("valueModel");
        }
        this.valueModel = valueModel;
    }


    public void setValue(Object value) {
        valueModel.setValue(value);
    }


    public Object getValue() {
        return valueModel.getValue();
    }


    public void addValueChangeListener(PropertyChangeListener l) {
        valueModel.addPropertyChangeListener(l);
    }


    public void removeValueChangeListener(PropertyChangeListener l) {
        valueModel.removePropertyChangeListener(l);
    }
}
