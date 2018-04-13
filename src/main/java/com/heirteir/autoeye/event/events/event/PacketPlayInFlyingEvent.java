package com.heirteir.autoeye.event.events.event;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInFlying;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PacketPlayInFlyingEvent extends Event {
    private final PacketPlayInFlying packet;

    public PacketPlayInFlyingEvent(AutoEyePlayer player, PacketPlayInFlying packet) {
        super(player);
        this.packet = packet;
    }
}
