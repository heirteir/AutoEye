package com.heirteir.autoeye.event;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.event.events.*;
import com.heirteir.autoeye.event.events.executor.EventExecutor;
import com.heirteir.autoeye.event.events.executor.executors.PacketPlayInAbilitiesEventExecutor;
import com.heirteir.autoeye.event.events.executor.executors.PacketPlayInFlyingEventExecutor;
import com.heirteir.autoeye.event.events.executor.executors.PlayerTeleportEventExecutor;
import com.heirteir.autoeye.event.events.executor.executors.PlayerVelocityEventExecutor;

import java.util.Map;
import java.util.Set;

public class EventHandler {
    private final Map<Class<? extends Event>, Set<EventExecutor>> executors;

    public EventHandler() {
        this.executors = Maps.newHashMap();
    }

    public void createCheckEventExecutors(Autoeye autoeye) {
        //check events
        this.register(PacketPlayInFlyingEvent.class, new PacketPlayInFlyingEventExecutor(autoeye));
        //updaters
        this.register(PlayerTeleportEvent.class, new PlayerTeleportEventExecutor(autoeye));
        this.register(PlayerVelocityEvent.class, new PlayerVelocityEventExecutor(autoeye));
        this.register(PacketPlayInAbilitiesEvent.class, new PacketPlayInAbilitiesEventExecutor(autoeye));
    }

    public void run(Event event) {
        for (EventExecutor executor : this.getExecutors(event.getClass())) {
            executor.run(event);
        }
    }

    public void register(Class<? extends Event> event, EventExecutor executor) {
        this.getExecutors(event).add(executor);
    }

    private Set<EventExecutor> getExecutors(Class<? extends Event> event) {
        return this.executors.computeIfAbsent(event, k -> Sets.newHashSet());
    }
}
