package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;

public class Timer extends Check<PacketPlayInFlyingEvent> {
    public Timer() {
        super("Timer");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        return event.getPlayer().getPhysics().getMovesPerSecond() > (21F + (20F - autoeye.getTps().get()));
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return true;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
