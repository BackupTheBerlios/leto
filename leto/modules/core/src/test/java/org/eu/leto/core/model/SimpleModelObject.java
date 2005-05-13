package org.eu.leto.core.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;


@Entity
public class SimpleModelObject extends AbstractModelObject {
    private String name;
    private Long id;
    private List<String> emails = new ArrayList<String>();
    private Date birthday;
    private transient int age;


    public SimpleModelObject() {
        super();
    }


    public SimpleModelObject(final String name, final Date birthday) {
        this();
        setName(name);
        setBirthday(birthday);
    }


    @Transient
    public int getAge() {
        return age;
    }


    public Date getBirthday() {
        return birthday;
    }


    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        age = (int) ((System.currentTimeMillis() - birthday.getTime()) / 1000d
                / 60d / 60d / 24d / 365.25d);
    }


    public List<String> getEmails() {
        return emails;
    }


    public void setEmails(List<String> emails) {
        this.emails = emails;
    }


    @Id
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
