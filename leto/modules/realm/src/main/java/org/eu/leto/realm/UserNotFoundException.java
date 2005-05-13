package org.eu.leto.realm;

public class UserNotFoundException extends IllegalStateException {
    private final String login;


    public UserNotFoundException(final String login) {
        super("No user found with login: '" + login + "'");
        this.login = login;
    }


    public String getLogin() {
        return login;
    }
}
