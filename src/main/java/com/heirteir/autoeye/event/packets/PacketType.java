package com.heirteir.autoeye.event.packets;

import lombok.Getter;

@Getter public enum PacketType {
    NULL(), PacketPlayInFlying("PacketPlayInPositionLook", "PacketPlayInPosition", "PacketPlayInLook"), PacketPlayOutPosition, PacketPlayInAbilities, PacketPlayOutEntityVelocity;
    private final String[] children;

    PacketType(String... types) {
        this.children = types;
    }

    public static PacketType fromString(String string) {
        for (PacketType packetType : PacketType.values()) {
            if (packetType.name().equals(string)) {
                return packetType;
            } else {
                for (String child : packetType.getChildren()) {
                    if (child.equals(string)) {
                        return packetType;
                    }
                }
            }
        }
        return NULL;
    }
}
