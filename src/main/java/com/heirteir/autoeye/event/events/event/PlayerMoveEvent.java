/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.events.event;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInFlying;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PlayerMoveEvent extends Event {
    private final PacketPlayInFlying packet;

    public PlayerMoveEvent(AutoEyePlayer player, PacketPlayInFlying packet) {
        super(player);
        this.packet = packet;
    }
}
