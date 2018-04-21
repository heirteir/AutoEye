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
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PlayerMoveEvent;

public class Speed extends Check {
    public Speed(Autoeye autoeye) {
        super(autoeye, "Speed");
    }

    @EventExecutor public boolean check(PlayerMoveEvent event) {
        if (event.getPlayer().isConnected() && event.getPlayer().getTimeData().getLastVelocity().getDifference() > 500 && event.getPlayer().getTimeData().getLastVelocity().getDifference() > 500 && event.getPlayer().getLocationData().isChangedPos() && !event.getPlayer().getPhysics().isFlying() && !event.getPlayer().getLocationData().isTeleported()) {
            float speed = (float) Math.sqrt(Math.pow(event.getPlayer().getPhysics().getClientVelocity().getX() - event.getPlayer().getPlayer().getVelocity().getX(), 2) + Math.pow(event.getPlayer().getPhysics().getClientVelocity().getZ() - event.getPlayer().getPlayer().getVelocity().getZ(), 2));
            float serverVelocity = (float) Math.sqrt(Math.pow(event.getPlayer().getPlayer().getVelocity().getX(), 2) + Math.pow(event.getPlayer().getPlayer().getVelocity().getZ(), 2));
            float walkSpeed;
            float angleDifference = this.autoeye.getMathUtil().angleDistance((float) (Math.atan2(event.getPlayer().getPhysics().getClientVelocity().getX(), event.getPlayer().getPhysics().getClientVelocity().getZ()) * (180F / Math.PI)), (float) (Math.atan2(event.getPlayer().getPlayer().getEyeLocation().getDirection().getX(), event.getPlayer().getPlayer().getEyeLocation().getDirection().getZ()) * (180F / Math.PI)));
            float safeAngleDifference = (angleDifference > 0 && angleDifference < 46) || angleDifference > 90 && angleDifference < 170 ? angleDifference > 46 ? angleDifference / (angleDifference / 45F) : angleDifference : 0;
            if (!event.getPlayer().getLocationData().isOnGround() && event.getPlayer().getLocationData().isInWater()) {
                walkSpeed = event.getPlayer().getPlayer().getWalkSpeed() * 0.8F;
                walkSpeed += (0.1 * event.getPlayer().getEnchantmentEffectAmplifier("DEPTH_STRIDER"));
            } else if (event.getPlayer().getLocationData().isInWeb()) {
                walkSpeed = event.getPlayer().getPlayer().getWalkSpeed() * 0.5F;
            } else {
                walkSpeed = event.getPlayer().getPlayer().getWalkSpeed();
            }
            float speedAmplifier = (0.1F * event.getPlayer().getPotionEffectAmplifier("SPEED"));
            walkSpeed += speedAmplifier;
            float normalWalkSpeed = event.getPlayer().getPlayer().getWalkSpeed() + speedAmplifier;
            if (!event.getPlayer().getLocationData().isHasSolidAbove() && !event.getPlayer().getLocationData().isInWater() && serverVelocity < walkSpeed * 2 && speed > walkSpeed * 3) {
                return true;
            } else if (angleDifference > 80 && event.getPlayer().getPlayer().isSprinting() && speed > .27 && !event.getPlayer().getPhysics().isHasVelocity()) {
                return this.checkThreshold(event.getPlayer(), 3, 100L);
            } else if (!event.getPlayer().getLocationData().isHasSolidAbove() && serverVelocity > walkSpeed * 0.58 && serverVelocity < walkSpeed && !event.getPlayer().getLocationData().isOnGround() && !event.getPlayer().getLocationData().isInWater()) {
                if (event.getPlayer().getLocationData().isOnIce()) {
                    if (speed > walkSpeed * 2.2) {
                        return this.checkThreshold(event.getPlayer(), 3, 500L);
                    }
                } else if (event.getPlayer().getLocationData().isOnStairs()) {
                    if (speed > walkSpeed * 2.9) {
                        return this.checkThreshold(event.getPlayer(), 3, 500L);
                    }
                } else if (event.getPlayer().getLocationData().isOnSlime()) {
                    if (speed > walkSpeed * 2.1) {
                        return this.checkThreshold(event.getPlayer(), 3, 500L);
                    }
                } else if (speed > walkSpeed + (safeAngleDifference * 0.01F)) {
                    return this.checkThreshold(event.getPlayer(), 4, 250L);
                }
            } else if (event.getPlayer().getLocationData().isInWater() || (event.getPlayer().getLocationData().isOnGround() && event.getPlayer().getPhysics().getCalculatedYAcceleration() <= 0 && serverVelocity == 0)) {
                if (event.getPlayer().getLocationData().isInWater()) {
                    if (speed > walkSpeed * 3) {
                        return true;
                    } else if (speed > walkSpeed) {
                        return this.checkThreshold(event.getPlayer(), 10, 500L);
                    }
                } else if (((walkSpeed == normalWalkSpeed && speed > walkSpeed * 1.46) || (walkSpeed != normalWalkSpeed && speed > walkSpeed) || (event.getPlayer().getLocationData().isOnStairs() || event.getPlayer().getLocationData().isOnStairs()))) {
                    if (event.getPlayer().getLocationData().isOnIce()) {
                        if (speed > walkSpeed * 2.2) {
                            return this.checkThreshold(event.getPlayer(), 3, 500L);
                        }
                    } else if (event.getPlayer().getLocationData().isOnStairs()) {
                        if (speed > walkSpeed * 2.9) {
                            return this.checkThreshold(event.getPlayer(), 3, 500L);
                        }
                    } else if (event.getPlayer().getLocationData().isOnSlime()) {
                        if (speed > walkSpeed * 3) {
                            return this.checkThreshold(event.getPlayer(), 3, 500L);
                        }
                    } else {
                        return speed > walkSpeed * 3 || this.checkThreshold(event.getPlayer(), 6, 200L);
                    }
                }
            }
            return this.resetThreshold(event.getPlayer());
        } else {
            return false;
        }
    }

    @Override public <T extends Event> boolean revert(T event) {
        event.getPlayer().teleport(event.getPlayer().getLocationData().getTeleportLocation());
        return false;
    }
}
