package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;

public class NoSlowDown extends Check {
    public NoSlowDown(Autoeye autoeye) {
        super(autoeye, "NoSlowDown");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        if (event.getPlayer().isConnected() && event.getPlayer().getTimeData().getLastVelocity().getDifference() > 500 && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported()) {
            float speed = (float) Math.sqrt(Math.pow(event.getPlayer().getPhysics().getClientVelocity().getX() - event.getPlayer().getPlayer().getVelocity().getX(), 2) + Math.pow(event.getPlayer().getPhysics().getClientVelocity().getZ() - event.getPlayer().getPlayer().getVelocity().getZ(), 2));
            float speedAmplifier = (0.1F * event.getPlayer().getPotionEffectAmplifier("SPEED"));
            float walkSpeed = event.getPlayer().getPlayer().getWalkSpeed() + speedAmplifier;
            return (speed > walkSpeed && (event.getPlayer().getPlayer().isBlocking() || event.getPlayer().getPlayer().isSneaking())) || (event.getPlayer().getPlayer().isSprinting() && (event.getPlayer().getPlayer().isBlocking() || event.getPlayer().getPlayer().isSneaking())) && this.checkThreshold(event.getPlayer(), 3, 100L); //add threshold to prevent false teleports
        } else {
            return false;
        }
    }

    @Override public <T extends Event> boolean revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
        return false;
    }
}
