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

public class SlimeJump extends Check {
    public SlimeJump(Autoeye autoeye) {
        super(autoeye, CheckType.MOVE_EVENT, "Slime Jump");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.isConnected() && player.getTimeData().getLastVelocity().getAmount() == 0 && player.getLocationData().isChangedPos() && player.getTimeData().getLastFlying().getAmount() == 0 && player.getLocationData().isOnSlime() && player.getPlayer().getVelocity().getY() > 0) {
            float difference = (float) Math.abs(player.getPhysics().getClientVelocity().getY() - player.getPlayer().getVelocity().getY());
            return (player.getPhysics().getOffGroundTicks() > 12 || player.getPhysics().getClientVelocity().getY() > player.getPhysics().getJumpVelocity()) && difference > 0.08F && (difference > 0.16F || this.checkThreshold(player, 2, 100L));
        } else {
            return false;
        }
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
