package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;

public class FastLadder extends Check {
    public FastLadder(Autoeye autoeye) {
        super(autoeye, "Fast Ladder");
    }

    @EventExecutor(event = PlayerMoveEvent.class) public boolean check(PlayerMoveEvent event) {
        if (event.getPlayer().isConnected() && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && event.getPlayer().getLocationData().isOnLadder()) {
            if (event.getPacket().isOnGround() || event.getPlayer().getPhysics().getOffGroundTicks() <= 4) {
                return event.getPlayer().getPhysics().getClientVelocity().getY() > event.getPlayer().getPhysics().getJumpVelocity() && (!(event.getPlayer().getPhysics().isHasVelocity() && event.getPlayer().getPlayer().getVelocity().getY() <= event.getPlayer().getPhysics().getClientVelocity().getY()) || this.checkThreshold(event.getPlayer(), 3, 100L));
            } else {
                float absVelocity = Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY());
                return absVelocity > 0.16F && (this.checkThreshold(event.getPlayer(), (int) Math.ceil(event.getPlayer().getPhysics().getJumpVelocity() / 0.16F), 100L));
            }
        } else {
            return false;
        }
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
