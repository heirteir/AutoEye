package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;

@Getter public class PacketPlayOutEntityVelocity extends PacketAbstract {
    private final float id;
    private final float x, y, z;
    private final boolean isPlayer;

    public PacketPlayOutEntityVelocity(Autoeye autoeye, AutoEyePlayer player, Object packet) {
        super(packet);
        WrappedClass packetPlayOutEntityVelocity = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayOutEntityVelocity");
        this.id = ((int) packetPlayOutEntityVelocity.getFieldByName("a").get(packet));
        if (this.isPlayer = player.getPlayer().getEntityId() == this.id) {
            this.x = ((int) packetPlayOutEntityVelocity.getFieldByName("b").get(packet)) / 8000F;
            this.y = ((int) packetPlayOutEntityVelocity.getFieldByName("c").get(packet)) / 8000F;
            this.z = ((int) packetPlayOutEntityVelocity.getFieldByName("d").get(packet)) / 8000F;
        } else {
            this.x = this.y = this.z = 0;
        }
    }
}
