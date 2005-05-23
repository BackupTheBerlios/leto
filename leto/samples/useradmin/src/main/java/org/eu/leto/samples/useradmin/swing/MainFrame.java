package org.eu.leto.samples.useradmin.swing;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.WindowConstants;

import org.eu.leto.core.application.Application;
import org.eu.leto.samples.useradmin.command.ExitCommand;
import org.eu.leto.samples.useradmin.command.ReloadUserListCommand;
import org.eu.leto.swing.AbstractFrame;


public class MainFrame extends AbstractFrame {
    private UserAdminPanel userAdminPanel;


    public MainFrame(final Application application) {
        super(application, "userAdmin");
        init();
    }


    private void init() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new MyWindowListener());
        buildUI();
    }


    private void buildUI() {
        userAdminPanel = new UserAdminPanel(getApplication());
        userAdminPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(userAdminPanel);
    }

    private class MyWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            new ExitCommand(getApplication()).execute();
        }


        @Override
        public void windowOpened(WindowEvent e) {
            new ReloadUserListCommand(getApplication()).execute();
            userAdminPanel.requestFocus();
        }
    }
}
