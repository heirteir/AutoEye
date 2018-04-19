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
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;

@Getter public class PacketPlayOutEntityVelocity extends PacketAbstract {
    private final float x, y, z;
    private final boolean isPlayer;

    public PacketPlayOutEntityVelocity(Autoeye autoeye, AutoEyePlayer player, Object packet) {
        super(packet);
        WrappedClass packetPlayOutEntityVelocity = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayOutEntityVelocity");
        if (this.isPlayer = ((int) packetPlayOutEntityVelocity.getFieldByName("a").get(packet)) == player.getPlayer().getEntityId()) {
            this.x = ((int) packetPlayOutEntityVelocity.getFieldByName("b").get(packet)) / 8000F;
            this.y = ((int) packetPlayOutEntityVelocity.getFieldByName("c").get(packet)) / 8000F;
            this.z = ((int) packetPlayOutEntityVelocity.getFieldByName("d").get(packet)) / 8000F;
        } else {
            this.x = this.y = this.z = 0;
        }
    }
}
