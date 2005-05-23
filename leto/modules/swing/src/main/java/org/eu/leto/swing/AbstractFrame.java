package org.eu.leto.swing;


import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.Application;
import org.eu.leto.core.application.ApplicationComponent;


public abstract class AbstractFrame extends JFrame implements
        ApplicationComponent {
    private final Application application;
    private final SwingServices services;
    private final Log log = LogFactory.getLog(getClass());
    private final String id;


    public AbstractFrame(final Application application, final String id) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id is required");
        }
        this.application = application;
        this.id = id;
        this.services = new SwingServices(application);

        init();
    }


    public final Application getApplication() {
        return application;
    }


    public final String getId() {
        return id;
    }


    public void setVisible(boolean b) {
        if (b) {
            // actually, it seems to be the best place for doing actions each
            // time
            // the frame is displayed
            loadFramePreferences();
        }

        super.setVisible(b);
    }


    public void saveFramePreferences() {
        if (log.isDebugEnabled()) {
            log.debug("Saving preferences for frame '" + id + "'");
        }

        final Preferences prefs = getPreferencesForFrame(application, id);
        final Rectangle bounds = getBounds();

        final int state = getExtendedState();

        // we don't save the position of the frame in maximized state
        if (state != Frame.MAXIMIZED_BOTH) {
            prefs.putInt("x", bounds.x);
            prefs.putInt("y", bounds.y);
            prefs.putInt("width", bounds.width);
            prefs.putInt("height", bounds.height);
            prefs.putBoolean("maximized", false);
        } else {
            prefs.remove("x");
            prefs.remove("y");
            prefs.remove("width");
            prefs.remove("height");
            prefs.putBoolean("maximized", true);
        }
    }


    private static Preferences getPreferencesForFrame(Application application,
            String id) {
        return application.getPreferences().node("windows").node(id);
    }


    private void init() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveFramePreferences();
            }
        });

        setTitle(application.getMessage(getId()));

        if (getServices().getImageCache() != null) {
            final String imagePath = application.getMessage(getId() + ".icon");
            if (imagePath != null) {
                final Image image = getServices().getImageCache().getImage(
                        imagePath);
                if (image != null) {
                    setIconImage(image);
                }
            }
        } else {
            if (log.isWarnEnabled()) {
                log
                        .warn("No icon configured for frame '"
                                + getId()
                                + "' since there is no ImageCache available in the application object register");
            }
        }
    }


    private void loadFramePreferences() {
        if (log.isDebugEnabled()) {
            log.debug("Loading preferences for frame '" + id + "'");
        }

        // we pack the frame to use the width and the height values as
        // default values
        pack();

        final int defaultWidth = getWidth();
        final int defaultHeight = getHeight();
        final Dimension screenSize = getToolkit().getScreenSize();
        final int defaultX = (screenSize.width - defaultWidth) / 2;
        final int defaultY = (screenSize.height - defaultHeight) / 2;

        final Preferences prefs = getPreferencesForFrame(application, id);
        final Rectangle bounds = new Rectangle();
        bounds.x = prefs.getInt("x", defaultX);
        bounds.y = prefs.getInt("y", defaultY);
        bounds.width = prefs.getInt("width", getWidth());
        bounds.height = prefs.getInt("height", getHeight());
        setBounds(bounds);
        if (prefs.getBoolean("maximized", false)) {
            setExtendedState(Frame.MAXIMIZED_BOTH);
        }
    }


    protected final SwingServices getServices() {
        return services;
    }
}
