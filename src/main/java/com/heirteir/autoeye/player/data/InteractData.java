/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.packets.wrappers.PacketPlayInUseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

@Getter @Setter public class InteractData {
    private Block lastBlock;
    private Entity lastEntity;
    private PacketPlayInUseEntity.ActionType lastActionType;
    private int hitsInLastSecond;
    private long hitTimer;
    private List<Integer> lastHits = new ArrayList<>();
}
