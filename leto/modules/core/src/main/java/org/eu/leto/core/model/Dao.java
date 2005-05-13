package org.eu.leto.core.model;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * Base interface for Data Access Objects.
 */
public interface Dao<T extends ModelObject> {
    boolean exists(Serializable id);


    List<T> findAll();


    T findById(Serializable id);


    List<T> findByIds(Collection<? extends Serializable> ids);


    void create(T obj);


    void delete(T obj);


    void update(T obj);
}
