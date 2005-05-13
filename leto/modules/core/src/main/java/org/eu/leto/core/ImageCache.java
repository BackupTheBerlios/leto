package org.eu.leto.core;


import java.awt.Image;


public interface ImageCache {
    Image getImage(String path);


    void setPrefix(String prefix);
}
