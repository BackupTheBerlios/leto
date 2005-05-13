package org.eu.leto.core.application;


import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang.NullArgumentException;
import org.eu.leto.core.AbstractImageCache;


public class ApplicationImageCache extends AbstractImageCache {
    private final Application application;


    public ApplicationImageCache(final Application application,
            final String prefix) {
        super(prefix);
        if (application == null) {
            throw new NullArgumentException("application");
        }
        this.application = application;
    }


    public ApplicationImageCache(final Application application) {
        this(application, null);
    }


    @Override
    protected Image loadObject(String key) {
        try {
            final URL url = application.getResource(key);

            return ImageIO.read(url);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading image: " + key);
        }
    }
}
