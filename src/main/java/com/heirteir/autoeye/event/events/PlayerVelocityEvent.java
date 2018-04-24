/*
 * Created by Justin Heflin on 4/19/18 8:21 PM
 * Copyright (c) 2018.
 *
 * Can be redistributed non commercially as long as credit is given to original copyright owner.
 *
 * last modified: 4/19/18 7:22 PM
 */
package com.heirteir.autoeye.event.events;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayOutEntityVelocity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PlayerVelocityEvent extends Event {
    private final PacketPlayOutEntityVelocity packet;

    public PlayerVelocityEvent(AutoEyePlayer player, PacketPlayOutEntityVelocity packet) {
        super(player);
        this.packet = packet;
    }
}
