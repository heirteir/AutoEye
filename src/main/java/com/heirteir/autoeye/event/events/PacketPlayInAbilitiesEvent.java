package com.heirteir.autoeye.event.events;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayInAbilities;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PacketPlayInAbilitiesEvent extends Event {
    private final PacketPlayInAbilities packetPlayOutAbilities;

    public PacketPlayInAbilitiesEvent(AutoEyePlayer player, PacketPlayInAbilities packetPlayOutAbilities) {
        super(player);
        this.packetPlayOutAbilities = packetPlayOutAbilities;
    }
}
