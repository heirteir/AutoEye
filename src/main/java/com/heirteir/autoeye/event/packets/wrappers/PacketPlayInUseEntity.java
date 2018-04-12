package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Entity;

@Getter public class PacketPlayInUseEntity extends PacketAbstract {
    private final ActionType actionType;
    private final Entity entity;

    public PacketPlayInUseEntity(Autoeye autoeye, World world, Object packet) {
        super(packet);
        WrappedClass packetPlayInUseEntity = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInUseEntity");
        this.actionType = ActionType.valueOf(((Enum) packetPlayInUseEntity.getFieldByName("action").get(packet)).name());
        int id = packetPlayInUseEntity.getFirstFieldByType(int.class).get(packet);
        Entity tempEntity = null;
        for (Entity entity : world.getEntities()) {
            if (entity.getEntityId() == id) {
                tempEntity = entity;
                break;
            }
        }
        this.entity = tempEntity;
    }

    public enum ActionType {
        ATTACK, INTERACT, INTERACT_ALL, INTERACT_AT, UNKNOWN
    }
}
