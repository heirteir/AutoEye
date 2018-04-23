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

public class Step extends Check {
    public Step(Autoeye autoeye) {
        super(autoeye, "Step");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        return event.getPlayer().isConnected() && event.getPlayer().getTimeData().getLastVelocity().getDifference() > 500 && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && !event.getPlayer().getLocationData().isOnPiston() && Math.abs(event.getPlayer().getPhysics().getCalculatedYVelocity()) < (event.getPlayer().getPhysics().getJumpVelocity() < 0.5625F ? 0.5625F : event.getPlayer().getPhysics().getJumpVelocity()) && (event.getPlayer().getPhysics().getJumpVelocity() < 0.5625F ? 0.5625F : event.getPlayer().getPhysics().getJumpVelocity()) < event.getPlayer().getPhysics().getClientVelocity().getY();
    }

    @Override public <T extends Event> boolean revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
        return false;
    }
}
