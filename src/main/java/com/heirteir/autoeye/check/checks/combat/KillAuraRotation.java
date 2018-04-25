/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.vector.Vector3D;

public class KillAuraRotation extends Check {
    public KillAuraRotation(Autoeye autoeye) {
        super(autoeye, "Kill Aura (Rotation)");
    }

    @Override
    public boolean check(AutoEyePlayer player) {
        if (player.getInteractData().getLastActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && player.getInteractData().getLastActionType() != null && player.getLocationData().getLocation().distance(new Vector3D((float) player.getInteractData().getLastEntity().getLocation().getX(), (float) player.getInteractData().getLastEntity().getLocation().getY(), (float) player.getInteractData().getLastEntity().getLocation().getZ())) > 1.25) {
            float previousYaw = autoeye.getMathUtil().angleTo180(player.getLocationData().getPreviousDirection().getX() + 90F);
            float yaw = autoeye.getMathUtil().angleTo180(player.getLocationData().getDirection().getX() + 90F);
            float dist = autoeye.getMathUtil().angleDistance((float) Math.toDegrees(autoeye.getMathUtil().yawToLocation(player, player.getInteractData().getLastEntity())), previousYaw);
            float yawDiff = autoeye.getMathUtil().angleDistance(yaw, previousYaw);
            float diff = dist - yawDiff;
            return ((Math.abs(yawDiff) > 5 && Math.abs(diff) < 3) || (Math.abs(yawDiff) > 2 && Math.abs(diff) < 1) || (Math.abs(Math.abs(diff) - Math.abs(dist)) > 20)) ? this.checkThreshold(player, 3) : this.resetThreshold(player);
        } else {
            return false;
        }
    }

    @Override public boolean revert(AutoEyePlayer player) {
        return true;
    }
}
