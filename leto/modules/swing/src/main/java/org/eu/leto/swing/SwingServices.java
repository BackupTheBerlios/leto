package org.eu.leto.swing;


import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.ImageCache;
import org.eu.leto.core.application.Application;


public class SwingServices {
    private final Log log = LogFactory.getLog(getClass());
    private final Application application;


    public SwingServices(final Application application) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        this.application = application;
    }


    public ComponentFactory getComponentFactory() {
        return (ComponentFactory) getObject(ComponentFactory.class);
    }


    public ImageCache getImageCache() {
        return (ImageCache) getObject(ImageCache.class);
    }


    private Object getObject(Class clazz) {
        final Object obj = application.getObjectRegister().getBeanOfType(clazz,
                false);
        if (obj == null) {
            if (log.isInfoEnabled()) {
                log.info("No object of type '" + clazz.getName()
                        + "' available in the application object register");
            }
        }

        return obj;
    }
}
