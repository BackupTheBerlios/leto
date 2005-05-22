package org.eu.leto.realm.util;


import java.util.Comparator;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.eu.leto.realm.User;


public class UserComparator implements Comparator<User> {
    private final SortBy sortBy;

    public static enum SortBy {
        DISPLAY_NAME, LOGIN
    }


    public UserComparator(final SortBy sortBy) {
        if (sortBy == null) {
            throw new NullArgumentException("sortBy");
        }
        this.sortBy = sortBy;
    }


    public UserComparator() {
        this(SortBy.LOGIN);
    }


    public int compare(User u1, User u2) {
        if (sortBy.equals(SortBy.DISPLAY_NAME)) {
            return new CompareToBuilder().append(u1.getDisplayName(),
                    u2.getDisplayName()).toComparison();
        } else
            if (sortBy.equals(SortBy.LOGIN)) {
                return u1.getLogin().compareTo(u2.getLogin());
            } else {
                throw new IllegalStateException("Sort field not handled");
            }
    }
}
