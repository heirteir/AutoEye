/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;

@Getter @Setter public class AttackData {
    private Entity lastEntity;
    private PacketPlayInUseEntity.ActionType lastActionType;
}
