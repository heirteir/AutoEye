package com.heirteir.autoeye.util.reflections.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor @Getter public class WrappedConstructor {
    private final WrappedClass parent;
    private final Constructor constructor;

    public Object newInstance(Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
