package org.eu.leto.samples.useradmin;


import org.apache.commons.lang.NullArgumentException;
import org.eu.leto.realm.User;


public class UserForm {
    private User user = new User();


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        if (user == null) {
            throw new NullArgumentException("user");
        }
        this.user = user;
    }
}
