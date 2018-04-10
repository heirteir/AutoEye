package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;

@Getter public class PacketPlayInAbilities extends PacketAbstract {
    private final boolean flying;

    public PacketPlayInAbilities(Autoeye autoeye, Object packet) {
        super(packet);
        WrappedClass packetPlayOutAbilities = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayInAbilities");
        this.flying = (boolean) packetPlayOutAbilities.getFieldByName("b").get(packet);
    }
}
