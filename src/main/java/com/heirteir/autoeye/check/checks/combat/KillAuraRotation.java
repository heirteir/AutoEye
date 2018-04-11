package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.PacketPlayInFlyingEvent;

public class KillAuraRotation extends Check<PacketPlayInFlyingEvent> {
    public KillAuraRotation() {
        super("KillAura (Rotation)");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        System.out.println(Math.abs(event.getPacket().getYaw() - event.getPlayer().getPhysics().getPreviousPacketPlayInFlying().getYaw()));
        return Math.abs(event.getPacket().getYaw() - event.getPlayer().getPhysics().getPreviousPacketPlayInFlying().getYaw()) > 45.0F && Math.abs(event.getPacket().getPitch() - event.getPlayer().getPhysics().getPreviousPacketPlayInFlying().getPitch()) < 2.0F && this.checkThreshold(event.getPlayer(), 5, 5000L);
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        return event.getPlayer().getPhysics().getPreviousPacketPlayInFlying() != null && event.getPlayer().getTimeData().getLastUseEntity().getDifference() < 300L;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {
    }
}
