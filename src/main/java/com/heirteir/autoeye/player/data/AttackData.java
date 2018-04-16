package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;

@Getter @Setter public class AttackData {
    private Entity lastEntity;
    public Double[] reachBuffer = new Double[] {0.0, 0.0, 0.0, 0.0};
    private PacketPlayInUseEntity.ActionType lastActionType;
}
