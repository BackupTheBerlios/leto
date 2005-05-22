package org.eu.leto.realm;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;


public class InMemoryUserDao implements UserDao {
    private Map<Long, User> userMap = new HashMap<Long, User>(1);


    public void setUsers(List<User> users) {
        for (final User user : users) {
            userMap.put(user.getUserId(), user);
        }
    }


    public User findByLogin(String login) {
        checkNullArgument("login", login);
        for (final User user : userMap.values()) {
            if (login.equals(user.getLogin())) {
                return user;
            }
        }

        throw new UserNotFoundException(login);
    }


    public boolean exists(Serializable id) {
        checkNullArgument("id", id);
        return userMap.keySet().contains(id);
    }


    public List<User> findAll() {
        return new ArrayList<User>(userMap.values());
    }


    public User findById(Serializable id) {
        checkNullArgument("id", id);
        final User user = userMap.get(id);
        if (user == null) {
            throw new IllegalStateException("No user found with id '" + id
                    + "'");
        }

        return user;
    }


    public List<User> findByIds(Collection<? extends Serializable> ids) {
        checkNullArgument("ids", ids);
        final List<User> list = new ArrayList<User>(ids.size());
        for (final Serializable id : ids) {
            list.add(findById(id));
        }

        return list;
    }


    public void create(User user) {
        // we are looking for the next id available for this user
        long nextId = 0;
        for (Long id : userMap.keySet()) {
            nextId = Math.max(id, nextId);
        }

        ++nextId;
        user.setUserId(nextId);
        userMap.put(nextId, user);
    }


    public void delete(User user) {
        checkNullArgument("user", user);
        userMap.remove(user.getUserId());
    }


    public void update(User user) {
        userMap.put(user.getUserId(), user);
    }


    private void checkNullArgument(String name, Object value) {
        if (value == null) {
            throw new NullArgumentException(name);
        }
    }
}
