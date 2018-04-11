package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInFlying;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.Vector;
import lombok.Getter;
import lombok.Setter;

@Getter public class Physics {
    public static final float GRAVITY = -0.07839966F;
    private PacketPlayInFlying previousPacketPlayInFlying;
    private Vector clientVelocity;
    private Vector clientAcceleration;
    private Vector serverVelocity;
    private Vector serverAcceleration;
    private float jumpVelocity;
    private float calculatedYVelocity;
    private float calculatedYAcceleration;
    private boolean moving;
    private int offGroundTicks;
    private boolean flying;
    private boolean hasVelocity;

    public void setPreviousPacketPlayInFlying(PacketPlayInFlying previousPacketPlayInFlying) {
        this.previousPacketPlayInFlying = previousPacketPlayInFlying;
    }

    public Physics(AutoEyePlayer player) {
        this.reset(player);
    }

    public void reset(AutoEyePlayer player) {
        this.previousPacketPlayInFlying = null;
        this.clientVelocity = new Vector(0, 0, 0);
        this.clientAcceleration = new Vector(0, 0, 0);
        this.serverVelocity = new Vector(0, 0, 0);
        this.serverAcceleration = new Vector(0, 0, 0);
        this.jumpVelocity = 0.42F;
        this.moving = false;
        this.calculatedYVelocity = 0;
        this.calculatedYAcceleration = 0;
        this.offGroundTicks = 0;
        this.flying = player.getPlayer().isFlying();
    }

    public void update(AutoEyePlayer player, PacketPlayInFlying flying) {
        if (flying.isChild() && flying.isHasPos()) {
            if (this.flying) {
                player.getTimeData().getLastFlying().update();
            }
            this.jumpVelocity = 0.42F;
            this.previousPacketPlayInFlying = this.previousPacketPlayInFlying == null ? flying : this.previousPacketPlayInFlying;
            this.moving = this.clientVelocity.getX() != 0 || this.clientVelocity.getY() != 0 || this.clientVelocity.getZ() != 0;
            this.clientAcceleration = this.clientVelocity;
            this.clientVelocity = new Vector(flying.getX() - previousPacketPlayInFlying.getX(), flying.getY() - previousPacketPlayInFlying.getY(), flying.getZ() - previousPacketPlayInFlying.getZ());
            this.clientAcceleration = new Vector(this.clientVelocity.getX() - this.clientAcceleration.getX(), this.clientVelocity.getY() - this.clientAcceleration.getY(), this.clientVelocity.getZ() - this.clientAcceleration.getZ());
            this.calculatedYAcceleration = this.calculatedYVelocity;
            if (!this.hasVelocity) {
                this.offGroundTicks++;
                if (this.flying || player.getLocationData().isTeleported() || player.getLocationData().isInWater() || player.getLocationData().isOnLadder() || player.getLocationData().isInWeb() || player.getTimeData().getLastFlying().getDifference() < 1000) {
                    this.calculatedYVelocity = this.clientVelocity.getY();
                } else if (flying.isOnGround()) {
                    this.calculatedYVelocity = 0;
                    this.offGroundTicks = 0;
                } else {
                    if ((previousPacketPlayInFlying.isOnGround() && this.clientVelocity.getY() > 0)) {
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
            this.serverAcceleration = this.serverVelocity;
            this.serverVelocity = new Vector((float) player.getPlayer().getVelocity().getX(), (float) player.getPlayer().getVelocity().getY(), (float) player.getPlayer().getVelocity().getZ());
            this.serverAcceleration = new Vector(this.serverVelocity.getX() - this.serverAcceleration.getX(), this.serverVelocity.getY() - this.serverAcceleration.getY(), this.serverVelocity.getZ() - this.serverAcceleration.getZ());
            this.calculatedYAcceleration = this.calculatedYVelocity - this.calculatedYAcceleration;
            this.hasVelocity = false;
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
