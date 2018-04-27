/*
 * Created by Justin Heflin on 4/19/18 8:31 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 8:21 PM
 */
package com.heirteir.autoeye.check.checks.movement;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class NoWeb extends Check {
    public NoWeb(Autoeye autoeye) {
        super(autoeye, CheckType.MOVE_EVENT, "No Web (Y)");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.isConnected() && player.getTimeData().getLastVelocity().getAmount() == 0 && player.getLocationData().isChangedPos() && player.getTimeData().getLastFlying().getAmount() == 0 && !player.getLocationData().isClientOnGround() && player.getLocationData().isInWeb()) {
            float absClientVelocity = Math.abs(player.getPhysics().getClientVelocity().getY());
            float absServerVelocity = (float) Math.abs(player.getPlayer().getVelocity().getY());
            return (absClientVelocity > 0.1 || absClientVelocity == 0) && ((absServerVelocity > 0.1 && absClientVelocity > absServerVelocity * 2) || this.checkThreshold(player, 3, 100L));
        } else {
            return false;
        }
    }

    @Override public boolean revert(AutoEyePlayer player) {
        player.teleport(player.getLocationData().getTeleportLocation());
        return false;
    }
}
