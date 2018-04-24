/*
 * Created by Justin Heflin on 4/19/18 8:31 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 8:26 PM
 */
package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class InvalidMotion extends Check {
    public InvalidMotion(Autoeye autoeye) {
        super(autoeye, "Invalid Motion");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.isConnected() && player.getTimeData().getLastVelocity().getDifference() > 500) {
            if (Math.abs(player.getPhysics().getClientVelocity().getY()) > 3.92) {
                return true;
            } else if (player.getLocationData().isChangedPos() && !player.getPhysics().isFlying() && !player.getLocationData().isTeleported() && !player.getLocationData().isOnPiston() && !player.getLocationData().isOnSlime() && !player.getLocationData().isInWater() && !player.getLocationData().isOnGround() && !player.getLocationData().isOnStairs() && !player.getLocationData().isOnLadder() && !player.getLocationData().isHasSolidAbove() && player.getTimeData().getLastInWeb().getDifference() > 150L) {
                if (player.getPhysics().getClientVelocity().getY() > player.getPhysics().getCalculatedYVelocity() + 0.001F && Math.abs(player.getPhysics().getClientAcceleration().getY() - player.getPhysics().getCalculatedYAcceleration()) > 0.002) {
                    return (player.getPhysics().getClientVelocity().getY() == 0 && player.getTimeData().getLastTeleport().getDifference() < 1000 && this.checkThreshold(player, 5, 500L)) || !((player.getPhysics().getClientVelocity().getY() == 0) || (player.getPhysics().getCalculatedYVelocity() > 0 && player.getPhysics().getCalculatedYVelocity() < 0.04)) || this.checkThreshold(player, 2, 100L);
                } else {
                    return Math.abs(player.getPhysics().getClientVelocity().getY() - player.getPhysics().getCalculatedYVelocity()) > 0.003 && Math.abs(player.getPhysics().getClientAcceleration().getY() - player.getPhysics().getCalculatedYAcceleration()) > 0.003 && ((player.getPhysics().getClientVelocity().getY() != -0.07839966F) || this.checkThreshold(player, 2, 100L));
                }
            }
        }
        return false;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
