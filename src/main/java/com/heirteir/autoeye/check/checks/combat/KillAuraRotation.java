package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.PacketPlayInUseEntityEvent;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;

public class KillAuraRotation extends Check<PacketPlayInUseEntityEvent> {
    public KillAuraRotation() {
        super("Kill Aura (Rotation)");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
        float previousYaw = autoeye.getMathUtil().angleTo180(event.getPlayer().getLocationData().getPreviousDirection().getX() + 90F);
        float yaw = autoeye.getMathUtil().angleTo180(event.getPlayer().getLocationData().getDirection().getX() + 90F);
        float dist = autoeye.getMathUtil().distance(autoeye.getMathUtil().radiansToDegrees(autoeye.getMathUtil().yawToLocation(event.getPlayer(), event.getPacket().getEntity())), previousYaw);
        float yawDiff = autoeye.getMathUtil().distance(yaw, previousYaw);
        float diff = Math.abs(dist - yawDiff);
        dist = Math.abs(dist);
        yawDiff = Math.abs(yawDiff);
        System.out.println(dist + " " + diff + " " + yawDiff);
        return ((yawDiff > 5 && diff < 3) || (yawDiff > 2 && diff < 1)) ? this.checkThreshold(event.getPlayer(), 2) : this.resetThreshold(event.getPlayer());
    }

    @Override public boolean canRun(PacketPlayInUseEntityEvent event) {
        return event.getPacket().getActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && event.getPacket().getEntity() != null;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
    }
}
