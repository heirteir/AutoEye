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

@Getter public class PacketPlayInFlying extends PacketAbstract {
    private final long time;
    private final boolean child;
    private final float x, y, z, yaw, pitch;
    private final boolean onGround, hasPos, hasLook;

    public PacketPlayInFlying(Autoeye autoeye, Object packet, boolean child) {
        super(packet);
        this.time = System.currentTimeMillis();
        this.child = child;
        WrappedClass packetPlayInFlying = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInFlying");
        this.x = ((Double) packetPlayInFlying.getFieldByName("x").get(packet)).floatValue();
        this.y = ((Double) packetPlayInFlying.getFieldByName("y").get(packet)).floatValue();
        this.z = ((Double) packetPlayInFlying.getFieldByName("z").get(packet)).floatValue();
        this.yaw = packetPlayInFlying.getFieldByName("yaw").get(packet);
        this.pitch = packetPlayInFlying.getFieldByName("pitch").get(packet);
        this.onGround = packetPlayInFlying.getFieldByName("f").get(packet);
        this.hasPos = packetPlayInFlying.getFieldByName("hasPos").get(packet);
        this.hasLook = packetPlayInFlying.getFieldByName("hasLook").get(packet);
    }
}
