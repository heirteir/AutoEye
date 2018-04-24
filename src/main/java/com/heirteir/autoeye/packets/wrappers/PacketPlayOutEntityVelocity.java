/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.packets.wrappers;

import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.Reflections;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import com.heirteir.autoeye.util.reflections.types.WrappedField;
import lombok.Getter;

@Getter public class PacketPlayOutEntityVelocity extends PacketAbstract {
    private static final WrappedField idField, xField, yField, zField;
    private final float x, y, z;
    private final boolean isPlayer;

    static {
        WrappedClass packetPlayOutEntityVelocity = Reflections.getNMSClass("PacketPlayOutEntityVelocity");
        idField = packetPlayOutEntityVelocity.getFieldByName("a");
        xField = packetPlayOutEntityVelocity.getFieldByName("b");
        yField = packetPlayOutEntityVelocity.getFieldByName("c");
        zField = packetPlayOutEntityVelocity.getFieldByName("d");
    }

    public PacketPlayOutEntityVelocity(AutoEyePlayer player, Object packet) {
        super(packet);
        if (this.isPlayer = ((int) idField.get(packet)) == player.getPlayer().getEntityId()) {
            this.x = ((int) xField.get(packet)) / 8000F;
            this.y = ((int) yField.get(packet)) / 8000F;
            this.z = ((int) zField.get(packet)) / 8000F;
        } else {
            this.x = this.y = this.z = 0;
        }
    }
}
