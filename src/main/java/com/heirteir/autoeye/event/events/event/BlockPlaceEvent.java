package com.heirteir.autoeye.event.events.event;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInBlockPlace;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class BlockPlaceEvent extends Event {
    private final PacketPlayInBlockPlace packet;

    public BlockPlaceEvent(AutoEyePlayer player, PacketPlayInBlockPlace packet) {
        super(player);
        this.packet = packet;
    }
}
