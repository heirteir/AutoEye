package com.heirteir.autoeye.event.events;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.heirteir.autoeye.event.events.event.Event;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Listener {
    private final Map<Class<? extends Event>, Set<Method>> handlers = Maps.newHashMap();
    @Getter private EventExecutor.Priority priority;

    public Listener(EventExecutor.Priority priority) {
        this.priority = priority;
        for (Method method : this.getClass().getMethods()) {
            EventExecutor executor = method.getAnnotation(EventExecutor.class);
            if (executor != null) {
                this.getEventExecutors(executor.event()).add(method);
            }
        }
    }

    public Set<Method> getEventExecutors(Class<? extends Event> event) {
        return this.handlers.computeIfAbsent(event, k -> Sets.newHashSet());
    }

    public Collection<Class<? extends Event>> getEventTypes() {
        return this.handlers.keySet();
    }
}
