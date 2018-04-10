package com.heirteir.autoeye.event.events;

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
