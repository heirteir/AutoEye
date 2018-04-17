package com.heirteir.autoeye.event.events.event;

import com.heirteir.autoeye.player.AutoEyePlayer;
import lombok.Getter;

@Getter public class PacketPlayInKeepAliveEvent extends Event {
    public PacketPlayInKeepAliveEvent(AutoEyePlayer player) {
        super(player);
    }
}