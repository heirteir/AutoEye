package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;

public class NoWeb extends Check<PacketPlayInFlyingEvent> {
    public NoWeb() {
        super("No Web (Y)");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        float absClientVelocity = Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY());
        float absServerVelocity = (float) Math.abs(event.getPlayer().getPlayer().getVelocity().getY());
        return (absClientVelocity > 0.1 || absClientVelocity == 0) && ((absServerVelocity > 0.1 && absClientVelocity > absServerVelocity * 2) || this.checkThreshold(event.getPlayer(), 3, 100L));
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return event.getPlayer().getLocationData().isInWeb();
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
