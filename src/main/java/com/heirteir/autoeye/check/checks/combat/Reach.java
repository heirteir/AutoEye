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
import com.heirteir.autoeye.check.CheckType;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import org.bukkit.entity.Player;

public class Reach extends Check {
    public Reach(Autoeye autoeye) {
        super(autoeye, CheckType.ENTITY_USE_EVENT, "Reach");
    }

    @Override public boolean check(AutoEyePlayer player) {
        if (player.getInteractData().getLastEntity() != null && player.getInteractData().getLastActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && (player.getInteractData().getLastEntity() instanceof Player)) {
            AutoEyePlayer attacked = autoeye.getAutoEyePlayerList().getPlayer((Player) player.getInteractData().getLastEntity());
            return Math.sqrt(Math.pow(attacked.getLocationData().getLocation().getX() - player.getLocationData().getLocation().getX(), 2) + Math.pow(attacked.getLocationData().getLocation().getZ() - player.getLocationData().getLocation().getZ(), 2)) > 5.5F && this.checkThreshold(player, 2, 500L);
        }
        return false;
    }

    @Override public boolean revert(AutoEyePlayer player) {
        return true;
    }
}
