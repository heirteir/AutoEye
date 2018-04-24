package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class NoSlowDown extends Check {
    public NoSlowDown(Autoeye autoeye) {
        super(autoeye, "NoSlowDown");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.isConnected() && player.getTimeData().getLastVelocity().getDifference() > 500 && player.getLocationData().isChangedPos() && !player.getPhysics().isFlying() && !player.getLocationData().isTeleported()) {
            float speed = (float) Math.sqrt(Math.pow(player.getPhysics().getClientVelocity().getX() - player.getPlayer().getVelocity().getX(), 2) + Math.pow(player.getPhysics().getClientVelocity().getZ() - player.getPlayer().getVelocity().getZ(), 2));
            float speedAmplifier = (0.1F * player.getPotionEffectAmplifier("SPEED"));
            float walkSpeed = player.getPlayer().getWalkSpeed() + speedAmplifier;
            return (speed > walkSpeed && (player.getPlayer().isBlocking() || player.getPlayer().isSneaking())) || (player.getPlayer().isSprinting() && (player.getPlayer().isBlocking() || player.getPlayer().isSneaking())) && this.checkThreshold(player, 3, 100L); //add threshold to prevent false teleports
        } else {
            return false;
        }
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
