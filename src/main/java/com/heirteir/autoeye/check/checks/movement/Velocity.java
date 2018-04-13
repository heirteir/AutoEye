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
        return event.getPlayer().getPhysics().getCalculatedYVelocity() > 0 && !event.getPlayer().getLocationData().isOnPiston() && !event.getPlayer().getLocationData().isOnSlime() && !event.getPlayer().getLocationData().isInWater() && !event.getPlayer().getLocationData().isOnStairs() && !event.getPlayer().getLocationData().isOnLadder() && event.getPlayer().getTimeData().getLastInWeb().getDifference() > 150L;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
