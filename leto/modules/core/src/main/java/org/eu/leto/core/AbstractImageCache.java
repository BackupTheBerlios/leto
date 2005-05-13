package org.eu.leto.core;


import java.awt.Image;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;


public abstract class AbstractImageCache extends AbstractCache<Image> implements
        ImageCache {
    private String prefix;


    public AbstractImageCache(final String prefix) {
        this.prefix = prefix;
    }


    public AbstractImageCache() {
        this(null);
    }


    public Image getImage(String path) {
        if (path == null) {
            throw new NullArgumentException("path");
        }

        return (Image) getObject((prefix == null) ? StringUtils.EMPTY
                : (prefix + path));
    }


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
