package com.heirteir.autoeye.event.events;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInAbilities;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PacketPlayInAbilitiesEvent extends Event {
    private final PacketPlayInAbilities packet;

    public PacketPlayInAbilitiesEvent(AutoEyePlayer player, PacketPlayInAbilities packet) {
        super(player);
        this.packet = packet;
    }
}
