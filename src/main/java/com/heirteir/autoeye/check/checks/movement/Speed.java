package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;

public class Speed extends Check<PacketPlayInFlyingEvent> {
    public Speed() {
        super("Speed");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        float vel = event.getPlayer().getPhysics().getServerVelocity().length();
        float speed1 = event.getPlayer().getPhysics().getClientVelocity().length();
        float speed2 = event.getPlayer().getPhysics().getPreviousClientVelocity().length();
        if (event.getPlayer().getPhysics().getOffGroundTicks() >= 2 && speed1 - vel > .36 && speed2 - vel > .36) {
            System.out.println(speed1 - vel + " " + (speed2 - vel));
            return false;
        }
        return this.resetThreshold(event.getPlayer());
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return true;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
