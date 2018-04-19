package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;

public class SlimeJump extends Check {
    public SlimeJump(Autoeye autoeye) {
        super(autoeye, "Slime Jump");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        if (event.getPlayer().isConnected() && (event.getPlayer().getPhysics().isHasVelocity() && !event.getPlayer().getPhysics().isPreviousVelocity()) && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported() && event.getPlayer().getLocationData().isOnSlime() && event.getPlayer().getPlayer().getVelocity().getY() > 0) {
            float difference = (float) Math.abs(event.getPlayer().getPhysics().getClientVelocity().getY() - event.getPlayer().getPlayer().getVelocity().getY());
            return (event.getPlayer().getPhysics().getOffGroundTicks() > 12 || event.getPlayer().getPhysics().getClientVelocity().getY() > event.getPlayer().getPhysics().getJumpVelocity()) && difference > 0.08F && (difference > 0.16F || this.checkThreshold(event.getPlayer(), 2, 100L));
        } else {
            return false;
        }
    }

    @Override public <T extends Event> void revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
    }
}
