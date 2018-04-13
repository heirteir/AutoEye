package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;

public class LiquidWalk extends Check<PacketPlayInFlyingEvent> {
    public LiquidWalk() {
        super("Liquid Walk");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        //        System.out.println(Math.abs(event.getPlayer().getPhysics().getServerAcceleration().getY()) + "");
        //        return Math.abs(event.getPlayer().getPhysics().getServerAcceleration().getY()) <= 0.002 && event.getPlayer().getPhysics().getServerAcceleration().getY() != 0 && event.getPlayer().getPhysics().getClientAcceleration().getY() - event.getPlayer().getPhysics().getServerAcceleration().getY() >= 0;
        return false;
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return event.getPlayer().getLocationData().isInWater() && !event.getPlayer().getLocationData().isOnGround();
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
    }
}
