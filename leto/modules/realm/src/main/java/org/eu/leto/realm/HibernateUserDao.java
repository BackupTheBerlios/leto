package org.eu.leto.realm;


import java.util.List;

import org.eu.leto.core.model.AbstractHibernateDao;


public class HibernateUserDao extends AbstractHibernateDao<User> implements
        UserDao {
    public HibernateUserDao() {
        super(User.class);
    }


    public User findByLogin(String login) {
        final List<User> users = findByProperty("login", login);
        if (users.isEmpty()) {
            throw new UserNotFoundException(login);
        }
        if (users.size() > 1) {
            // this is unlikely to happen...
            throw new IllegalStateException(
                    "Several users have the same login: '" + login + "'");
        }

        assert users.size() == 1 : "Only one user expected for login '" + login
                + "'";

        return users.get(0);
    }
}
