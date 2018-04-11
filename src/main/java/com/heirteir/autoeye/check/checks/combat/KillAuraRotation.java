package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.PacketPlayInFlyingEvent;

public class KillAuraRotation extends Check<PacketPlayInFlyingEvent> {

    /*
    * Created by Leo101 ~11/4/18
    *
    * Dont forget to commit :3
     */

    public KillAuraRotation() {
        super("KillAura (Rotation)");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInFlyingEvent event) {
        if (Math.abs(event.getPacket().getYaw() - event.getPlayer().getPhysics().getPreviousPacketPlayInFlying().getYaw()) > 45.0F
                && Math.abs(event.getPacket().getPitch() - event.getPlayer().getPhysics().getPreviousPacketPlayInFlying().getPitch()) < 2.0F) {
            return this.checkThreshold(event.getPlayer(), 5, 5000L);
        }
        return false;
    }

    @Override public boolean canRun(PacketPlayInFlyingEvent event) {
        // if System.currentTimeMills() - lastUseEntity < 300L
        return false;
    }

    @Override
    public void revert(Autoeye autoeye, PacketPlayInFlyingEvent event) {}
}
