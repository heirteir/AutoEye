package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.vector.Vector3D;
import lombok.Getter;

@Getter public class Physics {
    private Vector3D serverVelocity;
    private Vector3D clientVelocity;
    private Vector3D previousClientVelocity;
    private Vector3D clientAcceleration;
    private float jumpVelocity;
    private float calculatedYVelocity;
    private float calculatedYAcceleration;
    private boolean moving;
    private int offGroundTicks;
    private boolean flying;
    private boolean hasVelocity;
    private int movesPerSecond;

    public Physics(AutoEyePlayer player) {
        this.reset(player);
    }

    public void reset(AutoEyePlayer player) {
        this.serverVelocity = new Vector3D(0, 0, 0);
        this.previousClientVelocity = new Vector3D(0, 0, 0);
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
            this.serverVelocity = player.getWrappedEntity().getVelocity();
            if (this.flying) {
                player.getTimeData().getLastFlying().update();
            }
            this.jumpVelocity = 0.42F + (player.getPotionEffectAmplifier("SPEED") * 0.1F);
            this.moving = this.clientVelocity.getX() != 0 || this.clientVelocity.getY() != 0 || this.clientVelocity.getZ() != 0;
            this.previousClientVelocity = this.clientVelocity;
            this.clientVelocity = new Vector3D(player.getLocationData().getLocation().getX() - player.getLocationData().getPreviousLocation().getX(), player.getLocationData().getLocation().getY() - player.getLocationData().getPreviousLocation().getY(), player.getLocationData().getLocation().getZ() - player.getLocationData().getPreviousLocation().getZ());
            this.clientAcceleration = new Vector3D(this.clientVelocity.getX() - this.previousClientVelocity.getX(), this.clientVelocity.getY() - this.previousClientVelocity.getY(), this.clientVelocity.getZ() - this.previousClientVelocity.getZ());
            this.calculatedYAcceleration = this.calculatedYVelocity;
            if (!this.hasVelocity || player.getPhysics().getCalculatedYVelocity() == 0) {
                this.offGroundTicks++;
                if (this.flying || player.getLocationData().isTeleported() || player.getLocationData().isInWater() || player.getLocationData().isOnLadder() || player.getLocationData().isInWeb() || player.getTimeData().getLastFlying().getDifference() < 1000) {
                    this.calculatedYVelocity = this.clientVelocity.getY();
                    if (player.getLocationData().isOnGround()) {
                        this.calculatedYVelocity = 0;
                        this.offGroundTicks = 0;
                    }
                } else if (player.getLocationData().isOnGround()) {
                    this.calculatedYVelocity = 0;
                    this.offGroundTicks = 0;
                } else {
                    if ((player.getLocationData().isPreviousOnGround() && this.clientVelocity.getY() > 0)) {
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
        }
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public void setHasVelocity(boolean hasVelocity) {
        this.hasVelocity = hasVelocity;
    }

    public void setCalculatedYVelocity(float calculatedYVelocity) {
        this.calculatedYVelocity = calculatedYVelocity;
    }
}
