/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.events;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.api.AutoEyeInfractionEvent;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.checks.combat.KillAuraRotation;
import com.heirteir.autoeye.check.checks.combat.Reach;
import com.heirteir.autoeye.check.checks.movement.*;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.player.updaters.DataUpdater;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class EventHandler {
    private final Autoeye autoeye;
    private final Map<Class<? extends Event>, List<MethodListenerPair>> executors = Maps.newHashMap();

    public EventHandler(Autoeye autoeye) {
        this.autoeye = autoeye;
    }

    public void createCheckEventExecutors(Autoeye autoeye) {
        //combat
        this.register(new KillAuraRotation(autoeye));
        this.register(new Reach(autoeye));
        //movement
        this.register(new FastLadder(autoeye));
        this.register(new InvalidLocation(autoeye));
        this.register(new InvalidMotion(autoeye));
        this.register(new NoWeb(autoeye));
        this.register(new SlimeJump(autoeye));
        this.register(new Speed(autoeye));
        this.register(new SpoofedOnGroundPacket(autoeye));
        this.register(new Step(autoeye));
        this.register(new Timer(autoeye));
        this.register(new NoSlowDown(autoeye));
        this.register(new InvalidPitch(autoeye));
        //updater
        this.register(new DataUpdater(autoeye));
        //verifier
    }

    public boolean run(Event event) {
        for (MethodListenerPair entry : this.getExecutors(event.getClass())) {
            try {
                Object invoke = entry.getMethod().invoke(entry.getListener(), event);
                if (invoke != null && (boolean) invoke) {
                    AutoEyeInfractionEvent e = new AutoEyeInfractionEvent(event.getPlayer().getPlayer(), event.getPlayer().getInfractionData().getInfraction((Check) entry.getListener()));
                    Bukkit.getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        event.getPlayer().getInfractionData().addVL(event.getPlayer(), (Check) entry.getListener());
                        for (AutoEyePlayer player : this.autoeye.getAutoEyePlayerList().getPlayers().values()) {
                            player.sendMessage(this.autoeye, this.autoeye.getPluginLogger().translateColorCodes(e.getMessage()));
                        }
                        return ((Check) entry.getListener()).revert(event);
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return true;
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
