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
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class InvalidMotion extends Check {
    public InvalidMotion(Autoeye autoeye) {
        super(autoeye, CheckType.MOVE_EVENT, "Invalid Motion");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.isConnected() && player.getTimeData().getLastVelocity().getAmount() == 0) {
            if (Math.abs(player.getPhysics().getClientVelocity().getY()) > 3.92) {
                return true;
            } else if (player.getLocationData().isChangedPos() && player.getTimeData().getLastFlying().getAmount() == 0 && !player.getLocationData().isOnPiston() && !player.getLocationData().isOnSlime() && !player.getLocationData().isInWater() && player.getPhysics().getCalculatedYVelocity() != 0 && !player.getLocationData().isOnStairs() && !player.getLocationData().isOnLadder() && !player.getLocationData().isHasSolidAbove() && !player.getLocationData().isInWeb()) {
                if (player.getPhysics().getClientVelocity().getY() > player.getPhysics().getCalculatedYVelocity() + 0.001F && Math.abs(player.getPhysics().getClientAcceleration().getY() - player.getPhysics().getCalculatedYAcceleration()) > 0.002) {
                    return (player.getPhysics().getClientVelocity().getY() == 0 && this.checkThreshold(player, 5, 500L)) || !((player.getPhysics().getClientVelocity().getY() == 0) || (player.getPhysics().getCalculatedYVelocity() > 0 && player.getPhysics().getCalculatedYVelocity() < 0.04)) || this.checkThreshold(player, 2, 100L);
                } else {
                    return Math.abs(player.getPhysics().getClientVelocity().getY() - player.getPhysics().getCalculatedYVelocity()) > 0.02 && Math.abs(player.getPhysics().getClientAcceleration().getY() - player.getPhysics().getCalculatedYAcceleration()) > 0.003 && ((player.getPhysics().getClientVelocity().getY() != -0.07839966F) || this.checkThreshold(player, 2, 100L));
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
