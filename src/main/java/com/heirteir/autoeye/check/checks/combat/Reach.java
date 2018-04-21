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
import com.heirteir.autoeye.event.events.EventExecutor;
import com.heirteir.autoeye.event.events.event.Event;
import com.heirteir.autoeye.event.events.event.PacketPlayInUseEntityEvent;
import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import org.bukkit.entity.Player;

public class Reach extends Check {
    public Reach(Autoeye autoeye) {
        super(autoeye, "Reach");
    }

    @EventExecutor public boolean check(PacketPlayInUseEntityEvent event) {
        if (event.getPacket().getEntity() != null && event.getPacket().getActionType().equals(PacketPlayInUseEntity.ActionType.ATTACK) && (event.getPacket().getEntity() instanceof Player)) {
            AutoEyePlayer attacker = event.getPlayer();
            AutoEyePlayer attacked = autoeye.getAutoEyePlayerList().getPlayer((Player) event.getPacket().getEntity());
            return Math.sqrt(Math.pow(attacked.getLocationData().getLocation().getX() - attacker.getLocationData().getLocation().getX(), 2) + Math.pow(attacked.getLocationData().getLocation().getZ() - attacker.getLocationData().getLocation().getZ(), 2)) > 5.5F && this.checkThreshold(event.getPlayer(), 2, 500L);
        } else {
            return false;
        }
    }

    @Override public <T extends Event> boolean revert(T event) {
        return true;
    }
}
