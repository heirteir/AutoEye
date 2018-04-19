/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
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
        this.actionType = ActionType.fromString(((Enum) packetPlayInUseEntity.getFieldByName("action").get(packet)).name());
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
        ATTACK, UNKNOWN;

        public static ActionType fromString(String name) {
            for (ActionType actionType : ActionType.values()) {
                if (actionType.name().equals(name)) {
                    return actionType;
                }
            }
            return UNKNOWN;
        }
    }
}
