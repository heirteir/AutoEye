package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.PacketPlayInFlyingEvent;

public class SpoofedOnGroundPacket extends Check<PacketPlayInFlyingEvent> {
    public SpoofedOnGroundPacket() {
        super("Spoofed on Ground");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        if (event.getPacket().isOnGround() && !event.getPlayer().getLocationData().isServerOnGround()) {
            return this.checkThreshold(event.getPlayer(), 5);
        } else {
            return this.resetThreshold(event.getPlayer());
        }
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return !event.getPlayer().getLocationData().isInWater();
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
