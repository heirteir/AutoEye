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

public class SpoofedOnGroundPacket extends Check {
    public SpoofedOnGroundPacket(Autoeye autoeye) {
        super(autoeye, "Spoofed on Ground");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return (player.isConnected() && player.getTimeData().getLastVelocity().getDifference() > 500 && player.getLocationData().isChangedPos() && !player.getPhysics().isFlying() && !player.getLocationData().isTeleported() && player.getLocationData().isOnGround() != player.getLocationData().isServerOnGround()) ? this.checkThreshold(player, 5) : this.resetThreshold(player);
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
