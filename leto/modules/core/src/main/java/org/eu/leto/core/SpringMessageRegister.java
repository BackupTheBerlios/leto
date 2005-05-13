package org.eu.leto.core;


import java.util.Locale;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.context.MessageSource;


public class SpringMessageRegister extends AbstractMessageRegister {
    private final MessageSource messageSource;


    public SpringMessageRegister(final MessageSource messageSource) {
        super();
        if (messageSource == null) {
            throw new NullArgumentException("messageSource");
        }
        this.messageSource = messageSource;
    }


    @Override
    protected String getMessageInternal(String key, Object... args) {
        return messageSource.getMessage(key, args, null, Locale.getDefault());
    }
}
