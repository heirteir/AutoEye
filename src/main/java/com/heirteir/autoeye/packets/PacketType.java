/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.packets;

import lombok.Getter;

@Getter public enum PacketType {
    NULL(), PacketPlayInFlying("PacketPlayInPositionLook", "PacketPlayInPosition", "PacketPlayInLook"), PacketPlayInWindowClick, PacketPlayOutPosition, PacketPlayInKeepAlive, PacketPlayOutKeepAlive, PacketPlayInAbilities, PacketPlayInUseEntity, PacketPlayOutEntityVelocity;
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
