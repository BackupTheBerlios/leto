package org.eu.leto.samples.useradmin;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.lang.NullArgumentException;
import org.eu.leto.core.application.Application;


public class MainFrame extends JFrame {
    private final Application application;


    public MainFrame(final Application application) {
        super();
        if (application == null) {
            throw new NullArgumentException("application");
        }
        this.application = application;
        init();
    }


    private void init() {
        setTitle(application.getMessageRegister().getMessage(
                application.getId()));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new MyWindowListener());
    }

    private class MyWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            new ExitCommand(application).execute();
        }
    }
}
