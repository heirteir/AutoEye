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

public class Step extends Check {
    public Step(Autoeye autoeye) {
        super(autoeye, "Step");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        return event.getPlayer().isConnected() && (!event.getPlayer().getPhysics().isHasVelocity() && !event.getPlayer().getPhysics().isPreviousVelocity()) && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && !event.getPlayer().getLocationData().isOnPiston() && event.getPacket().isOnGround() && Math.abs(event.getPlayer().getPhysics().getCalculatedYVelocity()) < (event.getPlayer().getPhysics().getJumpVelocity() < 0.5F ? 0.5F : event.getPlayer().getPhysics().getJumpVelocity()) && (event.getPlayer().getPhysics().getJumpVelocity() < 0.5F ? 0.5F : event.getPlayer().getPhysics().getJumpVelocity()) < event.getPlayer().getPhysics().getClientVelocity().getY();
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
