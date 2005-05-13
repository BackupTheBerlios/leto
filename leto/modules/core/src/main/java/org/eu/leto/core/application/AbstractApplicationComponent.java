package org.eu.leto.core.application;


import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;


public class AbstractApplicationComponent implements ApplicationComponent {
    private final Application application;
    private final String id;


    public AbstractApplicationComponent(final Application application,
            final String id) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id is required");
        }
        this.application = application;
        this.id = id;
    }


    public final Application getApplication() {
        return application;
    }


    public final String getId() {
        return id;
    }
}
