package org.eu.leto.core.application;


import org.apache.commons.lang.NullArgumentException;


public class ApplicationException extends RuntimeException {
    private final Application application;
    private final String key;
    private final Object[] arguments;


    public ApplicationException(final Application application,
            final Throwable cause, final String key, final Object... arguments) {
        super(key, cause);
        if (application == null) {
            throw new NullArgumentException("application");
        }
        this.application = application;
        this.key = key;
        this.arguments = arguments;
    }


    public ApplicationException(final String msg, final Throwable cause) {
        super(msg, cause);
        this.application = null;
        this.key = null;
        this.arguments = null;
    }


    public Application getApplication() {
        return application;
    }


    public Object[] getArguments() {
        return arguments;
    }


    public String getKey() {
        return key;
    }


    public String getMessage() {
        if (key != null) {
            return application.getMessageRegister().getMessage(key, arguments);
        }

        return super.getMessage();
    }
}
