package org.eu.leto.swing;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.commons.lang.NullArgumentException;
import org.eu.leto.core.command.Command;


public class ActionCommandAdapter extends AbstractAction {
    private final Command command;


    public ActionCommandAdapter(final Command command) {
        if (command == null) {
            throw new NullArgumentException("command");
        }
        this.command = command;
    }


    public void actionPerformed(ActionEvent e) {
        command.execute();
    }
}
