/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;

@Getter public class PacketPlayInAbilities extends PacketAbstract {
    private final boolean flying;

    public PacketPlayInAbilities(Autoeye autoeye, Object packet) {
        super(packet);
        WrappedClass packetPlayOutAbilities = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInAbilities");
        this.flying = packetPlayOutAbilities.getFieldByName("b").get(packet);
    }
}
