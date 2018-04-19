package com.heirteir.autoeye.util.reflections.types;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

@Getter public class WrappedClass {
    private final Class parent;
    private final Map<String, WrappedField> cachedFields;
    private final Map<String, Set<WrappedMethod>> cachedMethods;

    public WrappedClass(Class parent) {
        this.parent = parent;
        this.cachedFields = Maps.newHashMap();
        this.cachedMethods = Maps.newHashMap();
    }

    private Set<WrappedMethod> getWrappedMethods(String name) {
        return this.cachedMethods.computeIfAbsent(name, k -> Sets.newHashSet());
    }

    public WrappedField getFieldByName(String name) {
        return this.cachedFields.computeIfAbsent(name, k -> {
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
        });
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
        for (WrappedField wrappedField : this.cachedFields.values()) {
            if (wrappedField.getType().equals(type)) {
                tempField = wrappedField;
                break;
            }
        }
        if (tempField == null) {
            for (Field field : this.parent.getDeclaredFields()) {
                if (field.getType().equals(type)) {
                    this.cachedFields.put(field.getName(), tempField = new WrappedField(this, field));
                    break;
                }
            }
        }
        return tempField;
    }

    public WrappedField getFirstFieldByType(Class<?> type) {
        return this.getFieldByType(type);
    }

    public WrappedMethod getMethod(String name, Class... parameters) {
        Set<WrappedMethod> methods = this.getWrappedMethods(name);
        for (WrappedMethod method : methods) {
            if (method.getMethod().getParameterTypes().length != parameters.length) {
                continue;
            }
            boolean same = true;
            for (int x = 0; x < method.getMethod().getParameterTypes().length; x++) {
                if (method.getMethod().getParameterTypes()[x] != parameters[x]) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return method;
            }
        }
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
                WrappedMethod output = new WrappedMethod(this, method);
                methods.add(output);
                return output;
            }
        }
        return null;
    }
}
