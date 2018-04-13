package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;

public class Step extends Check<PacketPlayInFlyingEvent> {
    public Step() {
        super("Step");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        return (event.getPlayer().getPhysics().getJumpVelocity() < 0.5F ? 0.5F : event.getPlayer().getPhysics().getJumpVelocity()) < event.getPlayer().getPhysics().getClientVelocity().getY();
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return event.getPacket().isOnGround();
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
