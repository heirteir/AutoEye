package com.heirteir.autoeye.event.events;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.checks.combat.KillAuraRotation;
import com.heirteir.autoeye.check.checks.interaction.ImpossibleInteraction;
import com.heirteir.autoeye.check.checks.movement.*;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.player.updaters.DataUpdater;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class EventHandler {
    private final Map<Class<? extends Event>, List<Listener>> listeners = Maps.newHashMap();

    public void createCheckEventExecutors(Autoeye autoeye) {
        //combat
        this.register(new KillAuraRotation(autoeye));
        //interactions
        this.register(new ImpossibleInteraction(autoeye));
        //movement
        this.register(new FastLadder(autoeye));
        this.register(new InvalidMotion(autoeye));
        this.register(new NoWeb(autoeye));
        this.register(new SlimeJump(autoeye));
        this.register(new Speed(autoeye));
        this.register(new SpoofedOnGroundPacket(autoeye));
        this.register(new Step(autoeye));
        this.register(new Timer(autoeye));
        this.register(new Velocity(autoeye));
        //updater
        this.register(new DataUpdater(autoeye));
    }

    public void run(Event event) {
        for (Listener listener : this.getListeners(event.getClass())) {
            for (Method method : listener.getEventExecutors(event.getClass())) {
                try {
                    Object invoke = method.invoke(listener, event);
                    if (method.getReturnType().equals(boolean.class)) {
                        if ((boolean) invoke) {
                            event.getPlayer().getInfractionData().addVL(event.getPlayer(), (Check) listener);
                            ((Check) listener).revert(event);
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void register(Listener listener) {
        for (Class<? extends Event> event : listener.getEventTypes()) {
            List<Listener> listeners = this.getListeners(event);
            listeners.add(listener);
            listeners.sort(Comparator.comparingInt(a -> a.getPriority().getLevel()));
        }
    }

    private List<Listener> getListeners(Class<? extends Event> event) {
        return this.listeners.computeIfAbsent(event, k -> Lists.newArrayList());
    }
}
