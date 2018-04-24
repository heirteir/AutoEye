/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util.reflections.types;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Getter public class WrappedClass {
    private final Class parent;

    public WrappedClass(Class parent) {
        this.parent = parent;
    }

    public WrappedField getFieldByName(String name) {
        Field tempField = null;
        for (Field field : this.parent.getDeclaredFields()) {
            if (field.getName().equals(name)) {
                tempField = field;
                break;
            }
        }
        if (tempField != null) {
            tempField.setAccessible(true);
            return new WrappedField(this, tempField);
        }
        return null;
    }

    public WrappedConstructor getConstructor(Class... types) {
        for (Constructor constructor : this.parent.getConstructors()) {
            if (Sets.newHashSet(constructor.getParameterTypes()).containsAll(Sets.newHashSet(types))) {
                return new WrappedConstructor(this, constructor);
            }
        }
        return null;
    }

    private WrappedField getFieldByType(Class<?> type) {
        WrappedField tempField = null;
        for (Field field : this.parent.getDeclaredFields()) {
            if (field.getType().equals(type)) {
                tempField = new WrappedField(this, field);
                break;
            }
        }
        return tempField;
    }

    public WrappedField getFirstFieldByType(Class<?> type) {
        return this.getFieldByType(type);
    }

    public WrappedMethod getMethod(String name, Class... parameters) {
        for (Method method : this.parent.getDeclaredMethods()) {
            if (!method.getName().equals(name) || parameters.length != method.getParameterTypes().length) {
                continue;
            }
            boolean same = true;
            for (int x = 0; x < method.getParameterTypes().length; x++) {
                if (method.getParameterTypes()[x] != parameters[x]) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return new WrappedMethod(this, method);
            }
        }
        return null;
    }

    public WrappedMethod getMethodByTypes(Class... parameters) {
        for (Method method : this.parent.getDeclaredMethods()) {
            if (parameters.length != method.getParameterTypes().length) {
                continue;
            }
            boolean same = true;
            for (int x = 0; x < method.getParameterTypes().length; x++) {
                if (method.getParameterTypes()[x] != parameters[x]) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return new WrappedMethod(this, method);
            }
        }
        return null;
    }
}
