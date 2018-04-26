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
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class SpoofedOnGroundPacket extends Check {
    public SpoofedOnGroundPacket(Autoeye autoeye) {
        super(autoeye, CheckType.MOVE_EVENT, "Spoofed on Ground");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return (player.isConnected() && !player.getLocationData().isTeleported() && !player.getPhysics().isFlying() && player.getLocationData().isClientOnGround() != player.getLocationData().isServerOnGround()) ? this.checkThreshold(player, 5) : this.resetThreshold(player);
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
