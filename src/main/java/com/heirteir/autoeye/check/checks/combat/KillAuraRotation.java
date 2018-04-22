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
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PacketPlayInUseEntityEvent;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.util.vector.Vector3D;

public class KillAuraRotation extends Check {
    public KillAuraRotation(Autoeye autoeye) {
        super(autoeye, "Kill Aura (Rotation)");
    }

    @EventExecutor public boolean check(PacketPlayInUseEntityEvent event) {
        if (event.getPacket().getActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && event.getPacket().getEntity() != null && event.getPlayer().getLocationData().getLocation().distance(new Vector3D((float) event.getPacket().getEntity().getLocation().getX(), (float) event.getPacket().getEntity().getLocation().getY(), (float) event.getPacket().getEntity().getLocation().getZ())) > 1.25) {
            float previousYaw = autoeye.getMathUtil().angleTo180(event.getPlayer().getLocationData().getPreviousDirection().getX() + 90F);
            float yaw = autoeye.getMathUtil().angleTo180(event.getPlayer().getLocationData().getDirection().getX() + 90F);
            float dist = autoeye.getMathUtil().angleDistance((float) Math.toDegrees(autoeye.getMathUtil().yawToLocation(event.getPlayer(), event.getPacket().getEntity())), previousYaw);
            float yawDiff = autoeye.getMathUtil().angleDistance(yaw, previousYaw);
            float diff = dist - yawDiff;
            return ((Math.abs(yawDiff) > 5 && Math.abs(diff) < 3) || (Math.abs(yawDiff) > 2 && Math.abs(diff) < 1) || (Math.abs(Math.abs(diff) - Math.abs(dist)) > 20)) ? this.checkThreshold(event.getPlayer(), 3) : this.resetThreshold(event.getPlayer());
        } else {
            return false;
        }
    }

    @Override public <T extends Event> boolean revert(T event) {
        return true;
    }
}
