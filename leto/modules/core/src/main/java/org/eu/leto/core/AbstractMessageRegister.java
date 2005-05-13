package org.eu.leto.core;


import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractMessageRegister implements MessageRegister {
    private final Log log = LogFactory.getLog(getClass());


    public final int getIntMessage(String key) {
        final String msg = getMessage(key);
        if (msg == null) {
            return 0;
        }

        try {
            return Integer.parseInt(msg);
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing message with key '"
                    + key + "': " + msg, e);
        }
    }


    public final String getMessage(String key, Object... args) {
        if (key == null) {
            throw new NullArgumentException("key");
        }

        String msg = null;

        try {
            msg = getMessageInternal(key, args);
        } catch (Exception e) {
            log.warn("Error while getting message with key " + key, e);
        }

        if (msg == null) {
            if (log.isInfoEnabled()) {
                log.info("No message found for key '" + key + "'");
            }
        }

        return msg;
    }


    protected abstract String getMessageInternal(String key, Object... args);
}
