package org.eu.leto.core.model;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractModelObject implements ModelObject {
    private final transient Log log = LogFactory.getLog(getClass());
    private static transient List<Field> cacheValuableFields;


    protected void checkNullArgument(String name, Object value) {
        if (value == null) {
            throw new NullArgumentException(name);
        }
    }


    protected void checkNullOrEmptyArgument(String name, CharSequence value) {
        checkNullArgument(name, value);
        if (value.length() == 0) {
            throw new IllegalStateException("Argument '" + name
                    + "' should not be empty");
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        final Class<? extends AbstractModelObject> clazz = getClass();
        if (obj == null || !(clazz.isAssignableFrom(obj.getClass()))) {
            return false;
        }

        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        final Class superClazz = clazz.getSuperclass();
        if (superClazz != null && !superClazz.equals(Object.class)
                && !superClazz.equals(AbstractModelObject.class)) {
            // the upper class is not Object not AbstractModelObject
            equalsBuilder.appendSuper(super.equals(obj));
        }

        try {
            for (final Field field : getValuableFields()) {
                equalsBuilder.append(field.get(this), field.get(obj));
            }
        } catch (Exception e) {
            throw new UnhandledException(e);
        }

        return equalsBuilder.isEquals();
    }


    @Override
    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        final Class superClazz = getClass().getSuperclass();
        if (superClazz != null && !superClazz.equals(Object.class)
                && !superClazz.equals(AbstractModelObject.class)) {
            // the upper class is not Object nor AbstractModelObject
            hashCodeBuilder.appendSuper(super.hashCode());
        }

        try {
            for (final Field field : getValuableFields()) {
                hashCodeBuilder.append(field.get(this));
            }
        } catch (Exception e) {
            throw new UnhandledException(e);
        }

        return hashCodeBuilder.toHashCode();
    }


    @Transient
    private List<Field> getValuableFields() {
        synchronized (this) {
            if (cacheValuableFields != null) {
                return cacheValuableFields;
            }
        }

        final Field[] declaredFields;
        try {
            declaredFields = getClass().getDeclaredFields();
        } catch (Exception e) {
            throw new UnhandledException(e);
        }

        final List<Field> fields = new ArrayList<Field>(declaredFields.length);

        for (final Field field : declaredFields) {
            final int mod = field.getModifiers();

            // static or transient fields and collections are not the welcome...
            if (Modifier.isStatic(mod) || Modifier.isTransient(mod)
                    || Collection.class.isAssignableFrom(field.getType())) {
                continue;
            }

            // ... neither fields which are not "valuable"
            final StringBuilder propertyName = new StringBuilder(field
                    .getName());
            propertyName.setCharAt(0, Character.toUpperCase(propertyName
                    .charAt(0)));
            if (!isValuableMethod("get" + propertyName)) {
                continue;
            }
            if (Boolean.class.isAssignableFrom(field.getType())
                    && !isValuableMethod("is" + propertyName)) {
                continue;
            }

            // we suppress the normal Java visibility, in order to access
            // private or protected fields
            field.setAccessible(true);
            fields.add(field);
        }

        if (log.isDebugEnabled()) {
            log.debug("Valuable fields for class '" + getClass().getName()
                    + "': " + fields);
        }

        synchronized (this) {
            cacheValuableFields = fields;
        }

        return fields;
    }


    @Transient
    private boolean isValuableMethod(String methodName) {
        try {
            final Method method = getClass().getMethod(methodName);
            if (method.isAnnotationPresent(Transient.class)
                    || method.isAnnotationPresent(Id.class)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
