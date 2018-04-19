/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
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
        return (event.getPlayer().isConnected() && (!event.getPlayer().getPhysics().isHasVelocity() && !event.getPlayer().getPhysics().isPreviousVelocity()) && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && event.getPacket().isOnGround() != event.getPlayer().getLocationData().isServerOnGround()) ? this.checkThreshold(event.getPlayer(), 5) : this.resetThreshold(event.getPlayer());
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
