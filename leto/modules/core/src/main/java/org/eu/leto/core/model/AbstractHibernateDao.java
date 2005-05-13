package org.eu.leto.core.model;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public abstract class AbstractHibernateDao<T extends ModelObject> extends
        HibernateDaoSupport implements Dao<T> {
    private final Class modelClass;


    public AbstractHibernateDao(final Class modelClass) {
        super();
        checkNullArgument("modelClass", modelClass);
        if (!ModelUtils.isModelClass(modelClass)) {
            throw new IllegalStateException("Class '" + modelClass
                    + "' is not a model class: " + modelClass.getName());
        }
        this.modelClass = modelClass;
    }


    public void create(T obj) {
        checkNullArgument("obj", obj);
        getHibernateTemplate().save(obj);
    }


    public void delete(T obj) {
        checkNullArgument("obj", obj);
        getHibernateTemplate().delete(obj);
    }


    public void update(T obj) {
        checkNullArgument("obj", obj);
        getHibernateTemplate().update(obj);
    }


    public T findById(Serializable id) {
        checkNullArgument("id", id);
        return (T) getHibernateTemplate().load(getModelClass(), id);
    }


    public List<T> findAll() {
        return (List<T>) getHibernateTemplate().loadAll(getModelClass());
    }


    public List<T> findByIds(Collection<? extends Serializable> ids) {
        checkNullArgument("ids", ids);
        final List<T> list = new ArrayList<T>();
        for (final Serializable id : ids) {
            list.add(findById(id));
        }
        return list;
    }


    public boolean exists(Serializable id) {
        checkNullArgument("id", id);
        return getHibernateTemplate().get(getModelClass(), id) != null;
    }


    protected List<T> findByProperty(final String name, final Object value) {
        checkNullArgument("name", name);
        checkNullArgument("value", value);
        return (List<T>) getHibernateTemplate().executeFind(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        return session.createQuery(
                                "from " + getModelClass().getName()
                                        + " as obj where obj." + name
                                        + "=:value").setParameter("value",
                                value).list();
                    }
                });
    }


    protected final Class getModelClass() {
        return modelClass;
    }


    protected void checkNullArgument(String name, Object value) {
        if (value == null) {
            throw new NullArgumentException(name);
        }
    }
}
