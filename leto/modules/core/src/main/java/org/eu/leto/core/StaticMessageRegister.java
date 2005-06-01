package org.eu.leto.core;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;


public class StaticMessageRegister extends AbstractMessageRegister {
    private final Map<String, String> messages = new HashMap<String, String>();


    @Override
    protected String getMessageInternal(String key, Object... args) {
        final String msg = messages.get(key);
        if (msg == null) {
            return null;
        }

        return MessageFormat.format(msg, args);
    }


    public void addMessage(String key, String msg) {
        if (key == null) {
            throw new NullArgumentException("key");
        }
        messages.put(key, msg);
    }
}
