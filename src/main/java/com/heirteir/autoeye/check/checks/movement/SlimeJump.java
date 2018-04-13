package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInFlyingEvent;

public class SlimeJump extends Check<PacketPlayInFlyingEvent> {
    public SlimeJump() {
        super("Slime Jump");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        float difference = (float) Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY() - event.getPlayer().getPlayer().getVelocity().getY());
        return (event.getPlayer().getPhysics().getOffGroundTicks() > 12 || event.getPlayer().getPhysics().getClientVelocity().getY() > event.getPlayer().getPhysics().getJumpVelocity()) && difference > 0.08F && (difference > 0.16F || this.checkThreshold(event.getPlayer(), 2, 100L));
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return event.getPlayer().getLocationData().isOnSlime() && event.getPlayer().getPlayer().getVelocity().getY() > 0;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
