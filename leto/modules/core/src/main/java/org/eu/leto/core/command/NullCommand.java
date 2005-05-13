package org.eu.leto.core.command;


import org.eu.leto.core.application.Application;


public class NullCommand extends AbstractCommand {
    public NullCommand(final Application application, final String id) {
        super(application, id);
    }


    protected void doExecute() throws Exception {
    }
}
