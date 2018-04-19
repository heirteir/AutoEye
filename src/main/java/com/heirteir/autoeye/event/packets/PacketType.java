/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
package com.heirteir.autoeye.event.packets;

import lombok.Getter;

@Getter public enum PacketType {
    NULL(), PacketPlayInFlying("PacketPlayInPositionLook", "PacketPlayInPosition", "PacketPlayInLook"), PacketPlayOutPosition, PacketPlayInKeepAlive, PacketPlayOutKeepAlive, PacketPlayInAbilities, PacketPlayInUseEntity, PacketPlayOutEntityVelocity, PacketPlayInBlockPlace;
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
