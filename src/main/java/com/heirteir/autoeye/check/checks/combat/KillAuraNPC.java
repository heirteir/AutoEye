package com.heirteir.autoeye.check.checks.combat;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.check.Check;
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.player.AutoEyePlayer;

public class KillAuraNPC extends Check {
    public KillAuraNPC(Autoeye autoeye) {
        super(autoeye, CheckType.ENTITY_USE_EVENT, "Kill Aura (npc)");
    }

    @Override public boolean check(AutoEyePlayer player) {
        return false;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        return false;
    }
}
