/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 8:20 PM
 */
package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;

public class SpoofedOnGroundPacket extends Check {
    public SpoofedOnGroundPacket(Autoeye autoeye) {
        super(autoeye, "Spoofed on Ground");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        return (event.getPlayer().isConnected() && event.getPlayer().getTimeData().getLastVelocity().getDifference() > 500 && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && event.getPacket().isOnGround() != event.getPlayer().getLocationData().isServerOnGround()) ? this.checkThreshold(event.getPlayer(), 5) : this.resetThreshold(event.getPlayer());
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
