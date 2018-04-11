package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;

@Getter public class PacketPlayInUseEntity extends PacketAbstract {
    private final ActionType actionType;
    private long time;

    public PacketPlayInUseEntity(Autoeye autoeye, Object packet) {
        super(packet);
        WrappedClass packetPlayInUseEntity = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInUseEntity");
        this.actionType = ActionType.valueOf(((Enum) packetPlayInUseEntity.getFieldByName("action").get(packet)).name());
        this.time = System.currentTimeMillis();
    }

    private enum ActionType {
        ATTACK, INTERACT, INTERACT_ALL, UNKNOWN
    }
}
