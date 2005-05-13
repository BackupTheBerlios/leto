package org.eu.leto.swing;


import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SplashScreenWindow extends JWindow {

    private final Image image;
    private final Log log = LogFactory.getLog(getClass());


    public SplashScreenWindow(final Image image) {
        super();
        this.image = image;
        buildUI();
    }


    private void buildUI() {
        final JLabel iconLabel = new JLabel(new ImageIcon(image));
        iconLabel.addMouseListener(new MyMouseListener());

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(iconLabel);
        setContentPane(panel);

        // let's minimize the window to the image dimension
        // and center the window on the screen
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    private class MyMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            log.info("Closing window upon user request");
            setVisible(false);
        }
    }
}
