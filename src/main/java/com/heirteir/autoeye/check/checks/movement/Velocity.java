package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;
import com.heirteir.autoeye.util.vector.Vector3D;

public class Velocity extends Check {
    public Velocity(Autoeye autoeye) {
        super(autoeye, "Velocity");
    }

    @EventExecutor(event = PlayerMoveEvent.class) public boolean check(PlayerMoveEvent event) {
        if (event.getPlayer().isConnected() && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && event.getPlayer().getPhysics().getCalculatedYVelocity() > 0 && event.getPlayer().getPhysics().isHasVelocity()) {
            Vector3D offset = event.getPlayer().getPhysics().getClientVelocity().absoluteValue().subtract(event.getPlayer().getPhysics().getServerVelocity().absoluteValue());
            return Math.abs(offset.getX()) > 0.09 || Math.abs(offset.getY()) > 0.09 || Math.abs(offset.getZ()) > 0.09;
        } else {
            return false;
        }
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
