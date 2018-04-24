/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.vector.Vector3D;
import lombok.Getter;
import lombok.Setter;

@Getter public class Physics {
    @Setter private Vector3D serverVelocity;
    private Vector3D clientVelocity;
    private Vector3D clientAcceleration;
    private float jumpVelocity;
    @Setter private float calculatedYVelocity;
    private float calculatedYAcceleration;
    private boolean moving;
    private int offGroundTicks;
    @Setter private boolean flying;
    @Setter private boolean hasVelocity;
    private boolean previousVelocity;
    @Setter private boolean startVelocity;
    private int movesPerSecond;

    public Physics(AutoEyePlayer player) {
        this.reset(player);
    }

    public void reset(AutoEyePlayer player) {
        this.serverVelocity = new Vector3D(0, 0, 0);
        this.clientVelocity = new Vector3D(0, 0, 0);
        this.clientAcceleration = new Vector3D(0, 0, 0);
        this.jumpVelocity = 0.42F;
        this.moving = false;
        this.calculatedYVelocity = 0;
        this.calculatedYAcceleration = 0;
        this.offGroundTicks = 0;
        this.flying = player.getPlayer().isFlying();
        this.movesPerSecond = 0;
    }

    public void update(AutoEyePlayer player) {
        if (player.getLocationData().isChangedPos()) {
            if (this.flying) {
                player.getTimeData().getLastFlying().update();
            } else {
                this.flying = player.getTimeData().getLastFlying().getDifference() < 1000;
            }
            this.jumpVelocity = 0.42F + (player.getPotionEffectAmplifier("SPEED") * 0.1F);
            this.moving = this.clientVelocity.getX() != 0 || this.clientVelocity.getY() != 0 || this.clientVelocity.getZ() != 0;
            this.clientAcceleration = this.clientVelocity;
            this.clientVelocity = new Vector3D(player.getLocationData().getLocation().getX() - player.getLocationData().getPreviousLocation().getX(), player.getLocationData().getLocation().getY() - player.getLocationData().getPreviousLocation().getY(), player.getLocationData().getLocation().getZ() - player.getLocationData().getPreviousLocation().getZ());
            this.clientAcceleration = new Vector3D(this.clientVelocity.getX() - this.clientAcceleration.getX(), this.clientVelocity.getY() - this.clientAcceleration.getY(), this.clientVelocity.getZ() - this.clientAcceleration.getZ());
            this.calculatedYAcceleration = this.calculatedYVelocity;
            this.startVelocity = this.hasVelocity;
            if (this.hasVelocity) {
                this.calculatedYVelocity = this.serverVelocity.getY();
            } else {
                this.offGroundTicks++;
                if (this.flying || player.getLocationData().isTeleported() || player.getLocationData().isInWater() || player.getLocationData().isOnLadder() || player.getLocationData().isInWeb() || player.getTimeData().getLastFlying().getDifference() < 1000) {
                    this.calculatedYVelocity = this.clientVelocity.getY();
                } else if (player.getLocationData().isOnGround()) {
                    this.calculatedYVelocity = 0;
                    this.offGroundTicks = 0;
                } else {
                    if (!this.previousVelocity && player.getLocationData().isPreviousOnGround() && this.clientVelocity.getY() > 0) {
                        this.calculatedYVelocity = jumpVelocity;
                    } else {
                        this.calculatedYVelocity -= 0.08F;
                        this.calculatedYVelocity *= 0.9800000190734863F;
                    }
                    if (this.calculatedYVelocity >= 0 && this.calculatedYVelocity <= 0.01 && this.clientVelocity.getY() <= 0 && this.clientVelocity.getY() >= -0.106) {
                        this.calculatedYVelocity = this.clientVelocity.getY();
                    }
                }
            }
            this.calculatedYAcceleration = this.calculatedYVelocity - this.calculatedYAcceleration;
            if (player.getTimeData().getLastTeleport().getDifference() < 800 || player.getTimeData().getSecondTick().getDifference() >= 1000L) {
                player.getTimeData().getSecondTick().update();
                this.movesPerSecond = 0;
            }
            this.movesPerSecond++;
            this.previousVelocity = this.hasVelocity;
            player.getTimeData().getLastMove().update();
        }
    }
}
