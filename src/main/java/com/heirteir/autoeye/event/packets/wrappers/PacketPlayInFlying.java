package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;

@Getter public class PacketPlayInFlying extends PacketAbstract {
    private final float x, y, z, yaw, pitch;
    private final boolean onGround, hasPos, hasLook;
    private boolean child;

    public PacketPlayInFlying(Autoeye autoeye, Object packet, boolean child) {
        super(packet);
        this.child = child;
        WrappedClass packetPlayInFlying = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInFlying");
        this.x = ((Double) packetPlayInFlying.getFieldByName("x").get(packet)).floatValue();
        this.y = ((Double) packetPlayInFlying.getFieldByName("y").get(packet)).floatValue();
        this.z = ((Double) packetPlayInFlying.getFieldByName("z").get(packet)).floatValue();
        this.yaw = (float) packetPlayInFlying.getFieldByName("yaw").get(packet);
        this.pitch = (float) packetPlayInFlying.getFieldByName("pitch").get(packet);
        this.onGround = (boolean) packetPlayInFlying.getFieldByName("f").get(packet);
        this.hasPos = (boolean) packetPlayInFlying.getFieldByName("hasPos").get(packet);
        this.hasLook = (boolean) packetPlayInFlying.getFieldByName("hasLook").get(packet);

    }
}
