/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.wrappers.WrappedEntity;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.util.Vector;

@Getter @Setter public class InteractData {
    private WrappedEntity lastEntity;
    private PacketPlayInUseEntity.ActionType lastActionType;
    private Vector previousDirection;
    private boolean inCombat;

    public void update(Autoeye autoeye, AutoEyePlayer player) {
        if (this.lastActionType != null && this.lastActionType.equals(PacketPlayInUseEntity.ActionType.ATTACK)) {
            this.inCombat = player.getTimeData().getLastUseEntity().getDifference() < 300;
        } else {
            this.inCombat = false;
        }
    }
}
