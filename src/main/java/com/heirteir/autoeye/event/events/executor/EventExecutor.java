package com.heirteir.autoeye.event.events.executor;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.event.Event;

public abstract class EventExecutor<T extends Event> {
    protected final Autoeye autoeye;

    public EventExecutor(Autoeye autoeye) {
        this.autoeye = autoeye;
    }

    public abstract void run(T event);
}
