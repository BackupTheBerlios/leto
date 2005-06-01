package org.eu.leto.core;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.NullArgumentException;


public class StaticObjectRegister extends AbstractObjectRegister {
    private final Map<String, Object> beans = new HashMap<String, Object>();
    private final Random random = new Random();


    @Override
    protected Object getBeanInternal(String name) {
        return beans.get(name);
    }


    @Override
    @SuppressWarnings("unchecked")
    protected Object getBeanOfTypeInternal(Class clazz) {
        if (clazz == null) {
            throw new NullArgumentException("clazz");
        }

        for (final Map.Entry<String, Object> entry : beans.entrySet()) {
            final Object bean = entry.getValue();
            if (bean == null) {
                continue;
            }
            if (clazz.isAssignableFrom(bean.getClass())) {
                return bean;
            }
        }

        return null;
    }


    public void addBean(String name, Object bean) {
        beans.put(name, bean);
    }


    public void addBean(Object bean) {
        beans.put("bean-" + random.nextInt(), bean);
    }
}
