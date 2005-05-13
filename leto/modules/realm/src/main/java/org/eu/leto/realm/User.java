package org.eu.leto.realm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eu.leto.core.model.AbstractAuditableModelObject;


@Entity
@Table(name = "user_account")
public class User extends AbstractAuditableModelObject {
    private Long userId;
    private String login;
    private String password;
    private Boolean disabled = Boolean.FALSE;
    private String displayName;


    @Id
    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Column(nullable = false, length = 32, unique = true)
    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        checkNullOrEmptyArgument("login", login);
        this.login = login;
    }


    @Column(nullable = false, length = 160)
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        checkNullArgument("password", password);
        this.password = password;
    }


    @Column(nullable = false)
    public Boolean getDisabled() {
        return disabled;
    }


    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }


    @Column(length = 128)
    public String getDisplayName() {
        return displayName;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
