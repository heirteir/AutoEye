package com.heirteir.autoeye.event.events;

import com.heirteir.autoeye.event.events.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) public @interface EventExecutor {
    Class<? extends Event> event();

    @RequiredArgsConstructor @Getter enum Priority {
        HIGH(0), NORMAL(1);
        private final int level;
    }
}
