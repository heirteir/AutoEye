package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;

public class Velocity extends Check<PacketPlayInFlyingEvent> {
    public Velocity() {
        super("Velocity");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        return Math.abs(event.getPlayer().getPhysics().getCalculatedYVelocity() - event.getPlayer().getPhysics().getClientVelocity().getY()) > 0.13;
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return event.getPlayer().getPhysics().getCalculatedYVelocity() > 0 && event.getPlayer().getPhysics().isHasVelocity();
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
