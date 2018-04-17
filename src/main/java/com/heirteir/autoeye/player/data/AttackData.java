package com.heirteir.autoeye.player.data;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;

@Getter @Setter public class AttackData {
    private Entity lastEntity;
    private float[] reachBuffer = new float[4];
    private PacketPlayInUseEntity.ActionType lastActionType;

    public void updateReachBuffer(float value) {
        float[] tempReachBuffer = new float[4];
        System.arraycopy(this.reachBuffer, 0, tempReachBuffer, 1, tempReachBuffer.length - 1);
        this.reachBuffer = tempReachBuffer;
        this.reachBuffer[0] = value;
    }
}
