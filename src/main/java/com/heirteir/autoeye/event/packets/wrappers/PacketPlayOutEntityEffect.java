package com.heirteir.autoeye.event.packets.wrappers;

import com.heirteir.autoeye.Autoeye;
import com.heirteir.autoeye.player.AutoEyePlayer;
import com.heirteir.autoeye.util.reflections.types.WrappedClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter public class PacketPlayOutEntityEffect extends PacketAbstract {
    private final boolean isPlayer;
    private final PotionEffectType type;
    private final byte amplifier;

    public PacketPlayOutEntityEffect(Autoeye autoeye, AutoEyePlayer player, Object packet) {
        super(packet);
        WrappedClass packetPlayOutEntityEffect = autoeye.getReflections().getPacketData().getWrappedPacketClass(autoeye.getReflections().getNetMinecraftServerString() + "PacketPlayOutEntityEffect");
        if (this.isPlayer = ((int) packetPlayOutEntityEffect.getFieldByName("a").get(packet)) == player.getPlayer().getEntityId()) {
            this.type = PotionEffectType.fromByte(packetPlayOutEntityEffect.getFieldByName("b").get(packet));
            this.amplifier = packetPlayOutEntityEffect.getFieldByName("c").get(packet);
        } else {
            this.type = PotionEffectType.UNKNOWN;
            this.amplifier = 0;
        }
    }

    @RequiredArgsConstructor @Getter public enum PotionEffectType {
        SPEED((byte) 1), UNKNOWN(Byte.MAX_VALUE);
        private final byte id;

        public static PotionEffectType fromByte(byte id) {
            for (PotionEffectType type : PotionEffectType.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return UNKNOWN;
        }
    }
}
