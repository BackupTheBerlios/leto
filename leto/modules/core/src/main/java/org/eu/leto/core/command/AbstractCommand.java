package org.eu.leto.core.command;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.AbstractApplicationComponent;
import org.eu.leto.core.application.Application;


public abstract class AbstractCommand extends AbstractApplicationComponent
        implements Command {
    private final Log log = LogFactory.getLog(getClass());


    public AbstractCommand(final Application application, final String id) {
        super(application, id);
    }


    public final void execute() {
        if (log.isDebugEnabled()) {
            log.debug("Executing command: " + getId());
        }

        try {
            doExecute();
        } catch (Exception e) {
            getApplication().raiseException(e, "error.command.execute");
        }
    }


    protected abstract void doExecute() throws Exception;
}
