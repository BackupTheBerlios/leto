package org.eu.leto.realm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.eu.leto.core.model.AbstractModelObject;


@Entity
public class UserRole extends AbstractModelObject {
    private Long userRoleId;
    private User user;
    private String role;


    public UserRole() {
        super();
    }


    public UserRole(final User user, final String role) {
        this();
        setUser(user);
        setRole(role);
    }


    @Column(nullable = false, length = 32)
    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        checkNullOrEmptyArgument("role", role);
        this.role = role;
    }


    @ManyToOne
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        checkNullArgument("user", user);
        this.user = user;
    }


    @Id
    public Long getUserRoleId() {
        return userRoleId;
    }


    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }
}
