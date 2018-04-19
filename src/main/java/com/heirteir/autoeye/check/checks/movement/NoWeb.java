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

public class NoWeb extends Check {
    public NoWeb(Autoeye autoeye) {
        super(autoeye, "No Web (Y)");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        if (event.getPlayer().isConnected() && (!event.getPlayer().getPhysics().isHasVelocity() && !event.getPlayer().getPhysics().isPreviousVelocity()) && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && event.getPlayer().getLocationData().isInWeb()) {
            float absClientVelocity = Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY());
            float absServerVelocity = (float) Math.abs(event.getPlayer().getPlayer().getVelocity().getY());
            return (absClientVelocity > 0.1 || absClientVelocity == 0) && ((absServerVelocity > 0.1 && absClientVelocity > absServerVelocity * 2) || this.checkThreshold(event.getPlayer(), 3, 100L));
        } else {
            return false;
        }
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
