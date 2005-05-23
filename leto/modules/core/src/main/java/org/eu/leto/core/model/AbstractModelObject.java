package org.eu.leto.core.model;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractModelObject implements ModelObject {
    private final transient Log log = LogFactory.getLog(getClass());
    private static transient Map<Class<? extends ModelObject>, List<Field>> cacheValuableFields = new HashMap<Class<? extends ModelObject>, List<Field>>();


    protected final void checkNullArgument(String name, Object value) {
        if (value == null) {
            throw new NullArgumentException(name);
        }
    }


    protected final void checkNullOrEmptyArgument(String name,
            CharSequence value) {
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
            for (final Field field : getValuableFields(getClass())) {
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
            for (final Field field : getValuableFields(getClass())) {
                hashCodeBuilder.append(field.get(this));
            }
        } catch (Exception e) {
            throw new UnhandledException(e);
        }

        return hashCodeBuilder.toHashCode();
    }


    @Override
    public String toString() {
        final ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        final Class superClazz = getClass().getSuperclass();
        if (superClazz != null && !superClazz.equals(Object.class)
                && !superClazz.equals(AbstractModelObject.class)) {
            // the upper class is not Object nor AbstractModelObject
            toStringBuilder.appendSuper(super.toString());
        }

        try {
            for (final Field field : getValuableFields(getClass())) {
                toStringBuilder.append(field.getName(), field.get(this));
            }
        } catch (Exception e) {
            throw new UnhandledException(e);
        }

        return toStringBuilder.toString();
    }


    @Transient
    private List<Field> getValuableFields(Class<? extends ModelObject> clazz) {
        synchronized (this) {
            final List<Field> fields = cacheValuableFields.get(clazz);

            // we reuse the already computed fields for this class
            if (fields != null) {
                return fields;
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
            // we cache the fields for a future use
            cacheValuableFields.put(clazz, fields);
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
