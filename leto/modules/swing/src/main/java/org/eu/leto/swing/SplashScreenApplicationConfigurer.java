package org.eu.leto.swing;


import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.imageio.ImageIO;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.Application;
import org.eu.leto.core.application.ApplicationConfigurerAdapter;


public class SplashScreenApplicationConfigurer extends
        ApplicationConfigurerAdapter {
    private final Log log = LogFactory.getLog(getClass());
    private final SplashScreenWindow splashScreenWindow;
    private final long minimumTime;


    public SplashScreenApplicationConfigurer(final String path,
            final long minimumTime) {
        super();
        if (minimumTime < 0) {
            throw new IllegalArgumentException("Invalid minimum time: "
                    + minimumTime);
        }

        final Image image;
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
            throw new IllegalStateException("Error while loading image: "
                    + path, e);
        }

        this.splashScreenWindow = new SplashScreenWindow(image);
        this.minimumTime = minimumTime * 1000;
    }


    public SplashScreenApplicationConfigurer(final String path) {
        this(path, 0);
    }


    public SplashScreenApplicationConfigurer(
            final SplashScreenWindow splashScreenWindow, final long minimumTime) {
        super();
        if (splashScreenWindow == null) {
            throw new NullArgumentException("splashScreenWindow");
        }
        if (minimumTime < 0) {
            throw new IllegalArgumentException("Invalid minimum time: "
                    + minimumTime);
        }
        this.splashScreenWindow = splashScreenWindow;
        this.minimumTime = minimumTime * 1000;
    }


    public void onInit(Application application) {
        new WaitThread().start();
    }

    private class WaitThread extends Thread implements WindowListener {

        private volatile boolean stop = false;


        public WaitThread() {
            splashScreenWindow.addWindowListener(this);
        }


        public void run() {
            if (SystemUtils.isJavaVersionAtLeast(1.3f)) {
                splashScreenWindow.setAlwaysOnTop(true);
            }

            splashScreenWindow.setVisible(true);

            long remaining = minimumTime;
            while (!stop && (remaining > 0)) {
                final long sleep = Math.min(250, remaining);
                try {
                    Thread.sleep(sleep);
                } catch (Exception e) {
                }

                remaining -= sleep;
            }

            splashScreenWindow.dispose();
            splashScreenWindow.removeWindowListener(this);
        }


        public void windowActivated(WindowEvent e) {
        }


        public void windowClosed(WindowEvent e) {
        }


        public void windowClosing(WindowEvent e) {
            stop = true;
            splashScreenWindow.removeWindowListener(this);
        }


        public void windowDeactivated(WindowEvent e) {
        }


        public void windowDeiconified(WindowEvent e) {
        }


        public void windowIconified(WindowEvent e) {
        }


        public void windowOpened(WindowEvent e) {
        }
    }
}
