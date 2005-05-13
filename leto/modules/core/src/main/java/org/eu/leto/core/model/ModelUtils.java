package org.eu.leto.core.model;


import java.io.Serializable;
import java.lang.reflect.Method;

import javax.persistence.Id;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.UnhandledException;


public final class ModelUtils {
    private ModelUtils() {
    }


    public static Serializable getIdValue(ModelObject obj) {
        if (obj == null) {
            throw new NullArgumentException("obj");
        }

        final Class clazz = obj.getClass();
        final String property = getIdProperty(clazz);
        final StringBuilder methodName = new StringBuilder("get");
        methodName.append(Character.toUpperCase(property.charAt(0)));
        if (property.length() > 1) {
            methodName.append(property.substring(1));
        }

        // we have the method name which looks like "getXXX" :
        // let's get the value

        final Object value;
        try {
            final Method method = clazz.getMethod(methodName.toString());
            value = method.invoke(obj);
        } catch (Exception e) {
            throw new UnhandledException("Unexpected error", e);
        }

        if (!(value instanceof Serializable)) {
            throw new IllegalStateException("The id value must implement the '"
                    + Serializable.class.getName() + "' interface: " + value);
        }

        return (Serializable) value;
    }


    public static boolean isModelClass(Class clazz) {
        return ModelObject.class.isAssignableFrom(clazz);
    }


    public static String getIdProperty(Class modelClass) {
        if (modelClass == null) {
            throw new NullArgumentException("modelClass");
        }
        if (!isModelClass(modelClass)) {
            throw new IllegalStateException("Class '" + modelClass.getName()
                    + "' is not a model class (which must implement '"
                    + ModelObject.class.getName() + "'):"
                    + modelClass.getName());
        }

        for (final Method method : modelClass.getDeclaredMethods()) {
            final String name = method.getName();
            if (name.length() < 4 && !name.startsWith("get")) {
                // this is not a "getter" method
                // please note that we do not test against "getter" method
                // which returns boolean value where the name may start with
                // "isXXX", because an id property should not be a boolean
                // value
                continue;
            }

            if (method.getAnnotation(Id.class) == null) {
                // an id property must have the Id annotation from the
                // EJB 3 API
                continue;
            }

            final StringBuilder property = new StringBuilder(name.substring(3));
            property.setCharAt(0, Character.toLowerCase(property.charAt(0)));

            return property.toString();
        }

        throw new IllegalStateException("No Id property found in class '"
                + modelClass.getName() + "'");
    }
}
