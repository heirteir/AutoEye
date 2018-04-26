/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 8:20 PM
 */
package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class Speed extends Check {
    public Speed(Autoeye autoeye) {
        super(autoeye, "Speed");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.isConnected() && player.getTimeData().getLastVelocity().getDifference() > 500 && player.getTimeData().getLastVelocity().getDifference() > 500 && player.getLocationData().isChangedPos() && !player.getPhysics().isFlying()) {
            float speed = (float) Math.sqrt(Math.pow(player.getPhysics().getClientVelocity().getX() - player.getPlayer().getVelocity().getX(), 2) + Math.pow(player.getPhysics().getClientVelocity().getZ() - player.getPlayer().getVelocity().getZ(), 2));
            float serverVelocity = (float) Math.sqrt(Math.pow(player.getPlayer().getVelocity().getX(), 2) + Math.pow(player.getPlayer().getVelocity().getZ(), 2));
            float walkSpeed;
            float angleDifference = this.autoeye.getMathUtil().angleDistance((float) (Math.atan2(player.getPhysics().getClientVelocity().getX(), player.getPhysics().getClientVelocity().getZ()) * (180F / Math.PI)), (float) (Math.atan2(player.getPlayer().getEyeLocation().getDirection().getX(), player.getPlayer().getEyeLocation().getDirection().getZ()) * (180F / Math.PI)));
            float safeAngleDifference = (angleDifference > 0 && angleDifference < 46) || angleDifference > 90 && angleDifference < 170 ? angleDifference > 46 ? angleDifference / (angleDifference / 45F) : angleDifference : 0;
            if (!player.getLocationData().isClientOnGround() && player.getLocationData().isInWater()) {
                walkSpeed = player.getPlayer().getWalkSpeed() * 0.8F;
                walkSpeed += (0.1 * player.getEnchantmentEffectAmplifier("DEPTH_STRIDER"));
            } else if (player.getLocationData().isInWeb()) {
                walkSpeed = player.getPlayer().getWalkSpeed() * 0.5F;
            } else {
                walkSpeed = player.getPlayer().getWalkSpeed();
            }
            float speedAmplifier = (0.1F * player.getPotionEffectAmplifier("SPEED"));
            walkSpeed += speedAmplifier;
            float normalWalkSpeed = player.getPlayer().getWalkSpeed() + speedAmplifier;
            if (!player.getLocationData().isHasSolidAbove() && !player.getLocationData().isInWater() && serverVelocity < walkSpeed * 2 && speed > walkSpeed * 3) {
                return true;
            } else if (angleDifference > 80 && player.getPlayer().isSprinting() && speed > .27 && !player.getPhysics().isHasVelocity()) {
                return this.checkThreshold(player, 3, 100L);
            } else if (!player.getLocationData().isHasSolidAbove() && serverVelocity > walkSpeed * 0.58 && serverVelocity < walkSpeed && !player.getLocationData().isClientOnGround() && !player.getLocationData().isInWater()) {
                if (player.getLocationData().isOnIce()) {
                    if (speed > walkSpeed * 2.2) {
                        return this.checkThreshold(player, 3, 500L);
                    }
                } else if (player.getLocationData().isOnStairs()) {
                    if (speed > walkSpeed * 2.9) {
                        return this.checkThreshold(player, 3, 500L);
                    }
                } else if (player.getLocationData().isOnSlime()) {
                    if (speed > walkSpeed * 2.1) {
                        return this.checkThreshold(player, 3, 500L);
                    }
                } else if (speed > walkSpeed + (safeAngleDifference * 0.01F)) {
                    return this.checkThreshold(player, 4, 250L);
                }
            } else if (player.getLocationData().isInWater() || (player.getLocationData().isClientOnGround() && player.getPhysics().getCalculatedYAcceleration() <= 0 && serverVelocity == 0)) {
                if (player.getLocationData().isInWater()) {
                    if (speed > walkSpeed * 3) {
                        return true;
                    } else if (speed > walkSpeed) {
                        return this.checkThreshold(player, 10, 500L);
                    }
                } else if (((walkSpeed == normalWalkSpeed && speed > walkSpeed * 1.46) || (walkSpeed != normalWalkSpeed && speed > walkSpeed) || (player.getLocationData().isOnStairs() || player.getLocationData().isOnStairs()))) {
                    if (player.getLocationData().isOnIce()) {
                        if (speed > walkSpeed * 2.2) {
                            return this.checkThreshold(player, 3, 500L);
                        }
                    } else if (player.getLocationData().isOnStairs()) {
                        if (speed > walkSpeed * 2.9) {
                            return this.checkThreshold(player, 3, 500L);
                        }
                    } else if (player.getLocationData().isOnSlime()) {
                        if (speed > walkSpeed * 3) {
                            return this.checkThreshold(player, 3, 500L);
                        }
                    } else {
                        return speed > walkSpeed * 3 || this.checkThreshold(player, 6, 200L);
                    }
                }
            }
            return this.resetThreshold(player);
        } else {
            return false;
        }
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
