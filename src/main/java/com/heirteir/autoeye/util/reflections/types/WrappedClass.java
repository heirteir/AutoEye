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

    public WrappedField getFieldAfterOtherByName(String name) {
        boolean next = false;
        for (Field field : this.parent.getDeclaredFields()) {
            if (next) {
                return this.getFieldByName(field.getName());
            } else {
                if (field.getName().equals(name)) {
                    next = true;
                }
            }
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

    private WrappedField getFieldByType(Class<?> type, boolean first) {
        WrappedField tempField = null;
        for (WrappedField wrappedField : this.cachedFields.values()) {
            if (wrappedField.getType().equals(type)) {
                tempField = wrappedField;
                if (first) {
                    break;
                }
            }
        }
        if (tempField == null) {
            for (Field field : this.parent.getDeclaredFields()) {
                if (field.getType().equals(type)) {
                    this.cachedFields.put(field.getName(), tempField = new WrappedField(this, field));
                    if (first) {
                        break;
                    }
                }
            }
        }
        return tempField;
    }

    public WrappedField getFirstFieldByType(Class<?> type) {
        return this.getFieldByType(type, true);
    }

    public WrappedField getLastFieldByType(Class<?> type) {
        return this.getFieldByType(type, false);
    }

    public WrappedMethod getMethodByTypes(Class... parameters) {
        Set<Class> tempParameters = Sets.newHashSet(parameters);
        for (Method method : this.parent.getDeclaredMethods()) {
            if (parameters.length == 0 || Sets.newHashSet(method.getParameterTypes()).containsAll(tempParameters)) {
                return new WrappedMethod(this, method);
            }
        }
        for (Method method : this.parent.getMethods()) {
            if (parameters.length == 0 || Sets.newHashSet(method.getParameterTypes()).containsAll(tempParameters)) {
                return new WrappedMethod(this, method);
            }
        }
        return null;
    }

    public WrappedMethod getMethod(String name, Class... parameters) {
        Set<WrappedMethod> methods = this.cachedMethods.get(name);
        Set<Class> tempParameters = Sets.newHashSet(parameters);
        WrappedMethod tempMethod = null;
        if (methods != null) {
            for (WrappedMethod method : methods) {
                if (parameters.length == 0 || method.getParameters().containsAll(tempParameters)) {
                    tempMethod = method;
                    break;
                }
            }
        }
        if (tempMethod == null) {
            for (Method method : this.parent.getDeclaredMethods()) {
                if (method.getName().equals(name)) {
                    if (parameters.length == 0 || tempParameters.containsAll(Sets.newHashSet(method.getParameterTypes()))) {
                        tempMethod = new WrappedMethod(this, method);
                        break;
                    }
                }
            }
            if (tempMethod == null) {
                for (Method method : this.parent.getMethods()) {
                    if (method.getName().equals(name)) {
                        if (parameters.length == 0 || tempParameters.containsAll(Sets.newHashSet(method.getParameterTypes()))) {
                            tempMethod = new WrappedMethod(this, method);
                            break;
                        }
                    }
                }
            }
        }
        return tempMethod;
    }
}
