package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.event.events.PacketPlayInUseEntityEvent;
import org.bukkit.GameMode;

public class Reach extends Check<PacketPlayInUseEntityEvent> {
    public Reach() {
        super("Reach");
    }

    @Override public boolean check(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
        return Math.sqrt(Math.pow(event.getPlayer().getPhysics().getPreviousPacketPlayInFlying().getX() - event.getPacket().getX(), 2) + Math.pow(event.getPlayer().getPhysics().getPreviousPacketPlayInFlying().getZ() - event.getPacket().getZ(), 2)) > (event.getPlayer().getPlayer().getGameMode().equals(GameMode.CREATIVE) ? 4.5 : 3.0);
    }

    @Override public boolean canRun(PacketPlayInUseEntityEvent event) {
        return true;
    }

    @Override public void revert(Autoeye autoeye, PacketPlayInUseEntityEvent event) {
    }
}
