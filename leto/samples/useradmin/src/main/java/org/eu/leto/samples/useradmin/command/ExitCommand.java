package org.eu.leto.samples.useradmin.command;


import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.AbstractCommand;


public class ExitCommand extends AbstractCommand {
    public ExitCommand(final Application application) {
        super(application, "exitCommand");
    }


    @Override
    protected void doExecute() throws Exception {
        getApplication().stop();
        getApplication().dispose();
    }
}
