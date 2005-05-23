package org.eu.leto.core;

/**
 * Interface to be implemented by objects that can resolve messages.
 */
public interface MessageRegister {
    /**
     * Resolve a message and convert it to an integer.
     * 
     * @param key key to lookup
     * @return the resolved integer, or 0
     */
    int getIntMessage(String key);


    /**
     * Resolve a message and convert it to a boolean.
     * 
     * @param key key to lookup
     * @return the resolved boolean, or false
     */
    boolean getBooleanMessage(String key);


    /**
     * Resolve a message, with one parameter. Parameters look like "{0}, {1},
     * etc..."
     * 
     * @param key key to lookup
     * @param args arguments for the message
     * @return the resolved message, or null
     */
    String getMessage(String key, Object... args);

}
