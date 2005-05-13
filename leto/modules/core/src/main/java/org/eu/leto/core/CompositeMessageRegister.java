package org.eu.leto.core;


import java.util.Collection;

import org.apache.commons.lang.NullArgumentException;


public class CompositeMessageRegister extends AbstractMessageRegister {
    private final Collection<MessageRegister> messageRegistries;


    public CompositeMessageRegister(
            final Collection<MessageRegister> messageRegisters) {
        super();
        if (messageRegisters == null) {
            throw new NullArgumentException("messageRegisters");
        }
        this.messageRegistries = messageRegisters;
    }


    @Override
    protected String getMessageInternal(String key, Object... args) {
        for (final MessageRegister messageRegister : messageRegistries) {
            final String msg = messageRegister.getMessage(key, args);
            if (msg != null) {
                return msg;
            }
        }

        return null;
    }
}
