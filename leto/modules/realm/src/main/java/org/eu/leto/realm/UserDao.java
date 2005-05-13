package org.eu.leto.realm;


import org.eu.leto.core.model.Dao;


public interface UserDao extends Dao<User> {
    User findByLogin(String login);
}
