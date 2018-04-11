package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.PacketPlayInFlyingEvent;

public class Timer extends Check<PacketPlayInFlyingEvent> {
    public Timer() {
        super("Timer");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        long difference = event.getPlayer().getTimeData().getDifference(event.getPlayer().getPhysics().getPreviousPacketPlayInFlying().getTime(), event.getPacket().getTime());
        return difference > 0 && difference < 45 && this.checkThreshold(event.getPlayer(), 2, 45L);
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return event.getPlayer().getPhysics().isMoving();
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
