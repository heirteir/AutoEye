package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import org.bukkit.entity.Player;

public class SelfHit extends Check {
    public SelfHit(Autoeye autoeye) {
        super(autoeye, CheckType.ENTITY_USE_EVENT, "SelfHit");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.getInteractData().getLastEntity() != null && player.getInteractData().getLastActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && (player.getInteractData().getLastEntity() instanceof Player)) {
            return player.getInteractData().getLastEntity().getEntityId() == player.getPlayer().getEntityId();
        }
        return false;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        return false;
    }
}
