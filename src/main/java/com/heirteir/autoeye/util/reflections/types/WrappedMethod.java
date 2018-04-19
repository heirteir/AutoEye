/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
package com.heirteir.autoeye.util.reflections.types;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@Getter public class WrappedMethod {
    private final WrappedClass parent;
    private final Method method;
    private final Set<Class> parameters;

    WrappedMethod(WrappedClass parent, Method method) {
        this.parent = parent;
        this.method = method;
        this.parameters = Sets.newHashSet(method.getParameterTypes());
    }

    public <T> T invoke(Object object, Object... args) {
        try {
            return (T) this.method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
