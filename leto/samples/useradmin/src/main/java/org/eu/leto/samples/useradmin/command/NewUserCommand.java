package org.eu.leto.samples.useradmin.command;


import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.AbstractCommand;
import org.eu.leto.samples.useradmin.swing.MainFrame;
import org.eu.leto.samples.useradmin.swing.UserDialog;


public class NewUserCommand extends AbstractCommand {
    public NewUserCommand(final Application application) {
        super(application, "action.new");
    }


    @Override
    protected void doExecute() throws Exception {
        final MainFrame owner = (MainFrame) getApplication().getBeanOfType(MainFrame.class);
        final UserDialog dialog = new UserDialog(getApplication(), owner);
        dialog.setVisible(true);
    }
}
