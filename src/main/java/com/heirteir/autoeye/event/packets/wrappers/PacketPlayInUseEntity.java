package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;

@Getter public class PacketPlayInUseEntity extends PacketAbstract {
    private final ActionType actionType;
    private final float x, y, z;

    public PacketPlayInUseEntity(Autoeye autoeye, Object packet) {
        super(packet);
        WrappedClass packetPlayInUseEntity = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInFlying");
        this.actionType = ActionType.valueOf(((Enum) packetPlayInUseEntity.getFieldByName("a").get(packet)).name());
        WrappedClass vec3DClass = autoeye.getReflections().getNMSClass("Vec3D");
        Object vec3D = packetPlayInUseEntity.getFirstFieldByType(vec3DClass.getParent()).get(packet);
        this.x = ((Double) vec3DClass.getFieldByName("a").get(vec3D)).floatValue();
        this.y = ((Double) vec3DClass.getFieldByName("b").get(vec3D)).floatValue();
        this.z = ((Double) vec3DClass.getFieldByName("c").get(vec3D)).floatValue();
    }

    private enum ActionType {
        ATTACK, INTERACT, INTERACT_ALL, UNKNOWN
    }
}
