package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;

@Getter @Setter public class AttackData {
    private Entity lastEntity;
    private PacketPlayInUseEntity.ActionType lastActionType;
}
