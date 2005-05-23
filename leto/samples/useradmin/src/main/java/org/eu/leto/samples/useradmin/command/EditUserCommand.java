package org.eu.leto.samples.useradmin.command;


import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.AbstractCommand;


public class EditUserCommand extends AbstractCommand {
    public EditUserCommand(final Application application) {
        super(application, "action.edit");
    }


    @Override
    protected void doExecute() throws Exception {
    }
}
