package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;

public class SpoofedOnGroundPacket extends Check<PacketPlayInFlyingEvent> {
    public SpoofedOnGroundPacket() {
        super("Spoofed on Ground");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        return event.getPacket().isOnGround() != event.getPlayer().getLocationData().isServerOnGround() ? this.checkThreshold(event.getPlayer(), 5) : this.resetThreshold(event.getPlayer());
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return true;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
