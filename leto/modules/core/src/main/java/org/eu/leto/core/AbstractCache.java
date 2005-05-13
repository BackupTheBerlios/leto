package org.eu.leto.core;


import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractCache<T> {
    private final Map<String, T> cache = new WeakHashMap<String, T>();
    private final Log log = LogFactory.getLog(getClass());


    protected final T getObject(String key) {
        T obj = cache.get(key);
        if (obj == null) {
            try {
                obj = loadObject(key);
                if (obj == null) {
                    if (log.isWarnEnabled()) {
                        log.warn("Unable to load object from cache: " + key);
                    }
                    return null;
                }
                putObject(key, obj);
            } catch (Exception e) {
                log.error("Error while loading object from cache: " + key, e);
            }
        }

        return obj;
    }


    protected final void putObject(String key, T obj) {
        cache.put(key, obj);
    }


    protected abstract T loadObject(String key) throws Exception;
}
