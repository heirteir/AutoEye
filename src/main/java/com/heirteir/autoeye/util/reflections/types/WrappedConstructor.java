/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.util.reflections.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor @Getter public class WrappedConstructor {
    private final WrappedClass parent;
    private final Constructor constructor;

    public <T> T newInstance(Object... args) {
        try {
            return (T) constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
