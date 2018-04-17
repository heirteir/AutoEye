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
        HIGH(0), NORMAL(1), LOW(2), LOWEST(3);
        private final int level;
    }
}
