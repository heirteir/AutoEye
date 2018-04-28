package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class AutoClicker extends Check {
    public AutoClicker(Autoeye autoeye) {
        super(autoeye, CheckType.ENTITY_USE_EVENT, "AutoClicker");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.getInteractData().getLastEntity() != null && player.getInteractData().getLastActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK)) {
            player.getInteractData().setHitsSinceLastAlivePacket(player.getInteractData().getHitsSinceLastAlivePacket() + 1);
            return player.getInteractData().getHitsSinceLastAlivePacket() > (30/*Make this number configurable?*/ * player.getTimeData().getLastInKeepAlive().getDifference() / 1000);
        }
        return false;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        return false;
    }
}
