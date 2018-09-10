package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;

import java.util.List;

public class AutoClicker extends Check {
    public AutoClicker(Autoeye autoeye) {
        super(autoeye, CheckType.ENTITY_USE_EVENT, "AutoClicker");
    }

    @Override
    public boolean check(AutoEyePlayer player) {
        if (player.getInteractData().getLastEntity() != null && player.getInteractData().getLastActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK)) {
            player.getInteractData().setHitsInLastSecond(player.getInteractData().getHitsInLastSecond() + 1);
            if (player.getTimeData().getDifference(player.getInteractData().getHitTimer() / 1000, System.currentTimeMillis() / 1000) >= 1) {
                int clicks = player.getInteractData().getHitsInLastSecond();
                player.getInteractData().setHitTimer(System.currentTimeMillis());
                player.getInteractData().setHitsInLastSecond(0);
                List<Integer> temp = player.getInteractData().getLastHits();
                if (temp.size() == 3) {
                    if (temp.get(0) == temp.get(1) && temp.get(1) == temp.get(2)) {
                        return true;
                    } else {
                        temp.remove(0);
                    }
                }
                temp.add(clicks);
                player.getInteractData().setLastHits(temp);
                return clicks >= 100;
            }
        }
        return false;
    }

    @Override
    public boolean revert(AutoEyePlayer player) {
        return false;
    }
}
