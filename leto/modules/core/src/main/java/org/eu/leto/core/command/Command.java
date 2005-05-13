package org.eu.leto.core.command;


import org.eu.leto.core.application.ApplicationComponent;


public interface Command extends ApplicationComponent {
    void execute();
}
