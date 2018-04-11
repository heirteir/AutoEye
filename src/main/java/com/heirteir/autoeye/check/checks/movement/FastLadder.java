package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.PacketPlayInFlyingEvent;

public class FastLadder extends Check<PacketPlayInFlyingEvent> {
    public FastLadder() {
        super("Fast Ladder");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        if (event.getPacket().isOnGround() || event.getPlayer().getPhysics().getOffGroundTicks() <= 4) {
            return event.getPlayer().getPhysics().getClientVelocity().getY() > event.getPlayer().getPhysics().getJumpVelocity() && (!(event.getPlayer().getPhysics().isHasVelocity() && event.getPlayer().getPlayer().getVelocity().getY() <= event.getPlayer().getPhysics().getClientVelocity().getY()) || this.checkThreshold(event.getPlayer(), 3, 100L));
        } else {
            float absVelocity = Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY());
            return absVelocity > 0.16F && (absVelocity > 0.32F || this.checkThreshold(event.getPlayer(), 3, 100L));
        }
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return event.getPlayer().getLocationData().isOnLadder();
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
