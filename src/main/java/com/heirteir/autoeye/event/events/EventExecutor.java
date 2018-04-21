/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) public @interface EventExecutor {
    Priority priority() default Priority.NORMAL;

    @RequiredArgsConstructor @Getter enum Priority {
        HIGHEST(0), HIGH(1), NORMAL(2), LOW(3), LOWEST(4);
        private final int level;
    }
}
