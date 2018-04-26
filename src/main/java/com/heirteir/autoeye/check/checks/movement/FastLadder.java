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

public class FastLadder extends Check {
    public FastLadder(Autoeye autoeye) {
        super(autoeye, "Fast Ladder");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.isConnected() && player.getTimeData().getLastVelocity().getDifference() > 500 && player.getLocationData().isChangedPos() && !player.getPhysics().isFlying() && player.getLocationData().isOnLadder()) {
            if (player.getLocationData().isClientOnGround() || player.getPhysics().getOffGroundTicks() <= 4) {
                return player.getPhysics().getClientVelocity().getY() > player.getPhysics().getJumpVelocity() && (!(!player.getPhysics().isHasVelocity() && player.getPlayer().getVelocity().getY() <= player.getPhysics().getClientVelocity().getY()) || this.checkThreshold(player, 3, 100L));
            } else {
                float absVelocity = Math.abs(player.getPhysics().getClientVelocity().getY());
                return absVelocity > 0.16F && (this.checkThreshold(player, (int) Math.ceil(player.getPhysics().getJumpVelocity() / 0.16F), 100L));
            }
        } else {
            return false;
        }
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
