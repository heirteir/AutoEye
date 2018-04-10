package com.heirteir.autoeye.event.events;

import com.heirteir.autoeye.event.packets.wrappers.PacketPlayOutEntityVelocity;
import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PlayerVelocityEvent extends Event {
    private final PacketPlayOutEntityVelocity packetPlayOutEntityVelocity;

    public PlayerVelocityEvent(AutoEyePlayer player, PacketPlayOutEntityVelocity packetPlayOutEntityVelocity) {
        super(player);
        this.packetPlayOutEntityVelocity = packetPlayOutEntityVelocity;
    }
}
