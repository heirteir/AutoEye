/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import lombok.Getter;

@Getter public class PacketPlayInFlying extends PacketAbstract {
    private static final WrappedField xField, yField, zField, yawField, pitchField, onGroundField, hasLookField, hasPosField;
    private final float x, y, z, yaw, pitch;
    private final boolean onGround, hasPos, hasLook;

    static {
        WrappedClass packetPlayInFlying = Reflections.getNMSClass("PacketPlayInFlying");
        xField = packetPlayInFlying.getFieldByName("x");
        yField = packetPlayInFlying.getFieldByName("y");
        zField = packetPlayInFlying.getFieldByName("z");
        yawField = packetPlayInFlying.getFieldByName("yaw");
        pitchField = packetPlayInFlying.getFieldByName("pitch");
        onGroundField = packetPlayInFlying.getFieldByName("f");
        hasPosField = packetPlayInFlying.getFieldByName("hasPos");
        hasLookField = packetPlayInFlying.getFieldByName("hasLook");
    }

    public PacketPlayInFlying(Object packet) {
        super(packet);
        this.x = ((Double) xField.get(packet)).floatValue();
        this.y = ((Double) yField.get(packet)).floatValue();
        this.z = ((Double) zField.get(packet)).floatValue();
        this.yaw = yawField.get(packet);
        this.pitch = pitchField.get(packet);
        this.onGround = onGroundField.get(packet);
        this.hasPos = hasPosField.get(packet);
        this.hasLook = hasLookField.get(packet);
    }
}
