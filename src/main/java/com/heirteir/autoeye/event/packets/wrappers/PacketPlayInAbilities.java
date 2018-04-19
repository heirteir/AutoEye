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

@Getter public class PacketPlayInAbilities extends PacketAbstract {
    private final boolean flying;

    public PacketPlayInAbilities(Autoeye autoeye, Object packet) {
        super(packet);
        WrappedClass packetPlayOutAbilities = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInAbilities");
        this.flying = packetPlayOutAbilities.getFieldByName("b").get(packet);
    }
}
