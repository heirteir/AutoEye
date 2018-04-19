/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:46 PM
 */
package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;

public class InvalidMotion extends Check {
    public InvalidMotion(Autoeye autoeye) {
        super(autoeye, "Invalid Motion");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        if (event.getPlayer().isConnected() && (!event.getPlayer().getPhysics().isHasVelocity() && !event.getPlayer().getPhysics().isPreviousVelocity()) && (!event.getPlayer().getPhysics().isHasVelocity() && !event.getPlayer().getPhysics().isPreviousVelocity()) && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && !event.getPlayer().getLocationData().isOnPiston() && !event.getPlayer().getLocationData().isOnSlime() && !event.getPlayer().getLocationData().isInWater() && !event.getPacket().isOnGround() && !event.getPlayer().getLocationData().isOnStairs() && !event.getPlayer().getLocationData().isOnLadder() && !event.getPlayer().getLocationData().isHasSolidAbove() && !(
                event.getPlayer().getPhysics().getClientVelocity().getY() == 0 && event.getPlayer().getLocationData().getSolidBlocks().getBlocks().size() > 0) && event.getPlayer().getTimeData().getLastInWeb().getDifference() > 150L) {
            if (event.getPlayer().getPhysics().getClientVelocity().getY() > event.getPlayer().getPhysics().getCalculatedYVelocity() + 0.001F && Math.abs(event.getPlayer().getPhysics().getClientAcceleration().getY() - event.getPlayer().getPhysics().getCalculatedYAcceleration()) > 0.002) {
                return (event.getPlayer().getPhysics().getClientVelocity().getY() == 0 && event.getPlayer().getTimeData().getLastTeleport().getDifference() < 1000 && this.checkThreshold(event.getPlayer(), 5, 500L)) || !((event.getPlayer().getPhysics().getClientVelocity().getY() == 0) || (event.getPlayer().getPhysics().getCalculatedYVelocity() > 0 && event.getPlayer().getPhysics().getCalculatedYVelocity() < 0.04)) || this.checkThreshold(event.getPlayer(), 2, 100L);
            } else {
                return Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY() - event.getPlayer().getPhysics().getCalculatedYVelocity()) > 0.001 && Math.abs(event.getPlayer().getPhysics().getClientAcceleration().getY() - event.getPlayer().getPhysics().getCalculatedYAcceleration()) > 0.002 && ((event.getPlayer().getPhysics().getClientVelocity().getY() != -0.07839966F) || this.checkThreshold(event.getPlayer(), 2, 100L));
            }
        } else {
            return false;
        }
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
