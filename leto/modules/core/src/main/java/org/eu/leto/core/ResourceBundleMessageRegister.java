package org.eu.leto.core;


import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.lang.NullArgumentException;


public class ResourceBundleMessageRegister extends AbstractMessageRegister {
    private final ResourceBundle resourceBundle;


    public ResourceBundleMessageRegister(final ResourceBundle resourceBundle) {
        super();
        if (resourceBundle == null) {
            throw new NullArgumentException("resourceBundle");
        }
        this.resourceBundle = resourceBundle;
    }


    @Override
    protected String getMessageInternal(String key, Object... args) {
        return MessageFormat.format(resourceBundle.getString(key), args);
    }
}
