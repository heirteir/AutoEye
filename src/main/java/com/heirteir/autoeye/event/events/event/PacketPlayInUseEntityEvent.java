/*
 * Created by Justin Heflin on 4/19/18 6:57 PM
 * Copyright (c) 2018.
 *
 * Code can not be redistributed under a non-commercial license, unless the owner of the copyright gives specific access to have commercial rights to the product.
 *
 * last modified: 4/19/18 6:51 PM
 */
package com.heirteir.autoeye.event.events.event;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInUseEntity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PacketPlayInUseEntityEvent extends Event {
    private final PacketPlayInUseEntity packet;

    public PacketPlayInUseEntityEvent(AutoEyePlayer player, PacketPlayInUseEntity packet) {
        super(player);
        this.packet = packet;
    }
}
