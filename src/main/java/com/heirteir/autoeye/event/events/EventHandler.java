package com.heirteir.autoeye.event.events;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.checks.combat.KillAuraRotation;
import com.heirteir.autoeye.check.checks.combat.Reach;
import com.heirteir.autoeye.check.checks.movement.*;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.player.updaters.DataUpdater;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class EventHandler {
    private final Map<Class<? extends Event>, List<MethodListenerPair>> executors = Maps.newHashMap();

    public void createCheckEventExecutors(Autoeye autoeye) {
        //combat
        this.register(new KillAuraRotation(autoeye));
        this.register(new Reach(autoeye));
        //movement
        this.register(new FastLadder(autoeye));
        this.register(new InvalidMotion(autoeye));
        this.register(new NoWeb(autoeye));
        this.register(new SlimeJump(autoeye));
        this.register(new Speed(autoeye));
        this.register(new SpoofedOnGroundPacket(autoeye));
        this.register(new Step(autoeye));
        this.register(new Timer(autoeye));
        //updater
        this.register(new DataUpdater(autoeye));
    }

    public void run(Event event) {
        for (MethodListenerPair entry : this.getExecutors(event.getClass())) {
            try {
                Object invoke = entry.getMethod().invoke(entry.getListener(), event);
                if (invoke != null && (boolean) invoke) {
                    event.getPlayer().getInfractionData().addVL(event.getPlayer(), (Check) entry.getListener());
                    ((Check) entry.getListener()).revert(event);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void register(Listener listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.getAnnotation(EventExecutor.class) != null) {
                List<MethodListenerPair> pairs = this.getExecutors((Class<? extends Event>) method.getParameterTypes()[0]);
                pairs.add(new MethodListenerPair(method, listener));
                pairs.sort(Comparator.comparingInt(a -> a.getMethod().getAnnotation(EventExecutor.class).priority().getLevel()));
            }
        }
    }

    private List<MethodListenerPair> getExecutors(Class<? extends Event> event) {
        return this.executors.computeIfAbsent(event, k -> Lists.newArrayList());
    }
}
