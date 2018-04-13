package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.event.PacketPlayInUseEntityEvent;
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
        float diff = dist - yawDiff;
        return ((Math.abs(yawDiff) > 5 && Math.abs(diff) < 3) || (Math.abs(yawDiff) > 2 && Math.abs(diff) < 1) || (Math.abs(Math.abs(diff) - Math.abs(dist)) > 20)) ? this.checkThreshold(event.getPlayer(), 3) : this.resetThreshold(event.getPlayer());
    }

    @Override public boolean canRun(PacketPlayInUseEntityEvent event) {
        return event.getPacket().getActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && event.getPacket().getEntity() != null;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
    }
}
