package com.heirteir.autoeye.event.events.event;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInKeepAlive;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PacketPlayInKeepAliveEvent extends Event {
    private final PacketPlayInKeepAlive packet;

    public PacketPlayInKeepAliveEvent(AutoEyePlayer player, PacketPlayInKeepAlive packet) {
        super(player);
        this.packet = packet;
    }
}